package com.example.jozmusik.UI.Playlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jozmusik.Adapter.PlaylistAdapter
import com.example.jozmusik.Data.Api.RetrofitClient
import com.example.jozmusik.Data.AppDatabase
import com.example.jozmusik.Data.Preferences.PreferenceManager
import com.example.jozmusik.Data.Repository.PlaylistRepository
import com.example.jozmusik.UI.ViewModelFactory
import com.example.jozmusik.databinding.FragmentPlaylistBinding

class PlaylistFragment : Fragment() {
    private var _binding: FragmentPlaylistBinding? = null
    private val binding get() = _binding!!
    private lateinit var playlistAdapter: PlaylistAdapter

    private val viewModel: PlaylistViewModel by viewModels {
        val database = AppDatabase.getDatabase(requireContext())
        val prefManager = PreferenceManager(requireContext())

        ViewModelFactory(
            PlaylistRepository(
                RetrofitClient.apiService,
                database.playlistDao(),
                prefManager
            ),
            prefManager,
            database.playlistDao()  // Tambahkan playlistDao ke factory
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaylistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        playlistAdapter = PlaylistAdapter { song ->
            viewModel.removeFromPlaylist(song)
            Toast.makeText(requireContext(), "Removed from playlist", Toast.LENGTH_SHORT).show()
        }

        binding.rvPlaylist.apply {
            adapter = playlistAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeViewModel() {
        viewModel.playlistSongs.observe(viewLifecycleOwner) { songs ->
            playlistAdapter.submitList(songs)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}