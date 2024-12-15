package com.example.jozmusik.UI.Home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jozmusik.Adapter.SongAdapter
import com.example.jozmusik.Data.Api.RetrofitClient
import com.example.jozmusik.Data.AppDatabase
import com.example.jozmusik.Data.Preferences.PreferenceManager
import com.example.jozmusik.Data.Repository.SongRepository
import com.example.jozmusik.UI.ViewModelFactory
import com.example.jozmusik.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels {
        val database = AppDatabase.getDatabase(requireContext())
        ViewModelFactory(
            SongRepository(
                apiService = RetrofitClient.apiService,
                songDao = database.songDao(),
                playlistDao = database.playlistDao(),
                prefManager = PreferenceManager(requireContext())
            ),
            PreferenceManager(requireContext()),
            database.playlistDao()  // Tambahkan parameter playlistDao ke factory
        )
    }
    private lateinit var songAdapter: SongAdapter
    private lateinit var prefManager: PreferenceManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefManager = PreferenceManager(requireContext())
        setupWelcomeMessage()
        setupRecyclerView()
        observeViewModel()
    }

    private fun setupWelcomeMessage() {
        binding.tvWelcome.text = "Hi, ${prefManager.getUsername()}"
    }

    private fun setupRecyclerView() {
        songAdapter = SongAdapter { song ->
            viewModel.addToPlaylist(song)
        }

        binding.rvSongs.apply {
            adapter = songAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeViewModel() {
        viewModel.songs.observe(viewLifecycleOwner) { songs ->
            songAdapter.submitList(songs)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            // Show/hide loading indicator
            binding.progressBar.isVisible = isLoading  // Remove the ? operator
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                val message = when {
                    it.contains("Playlist limit reached") ->
                        "Unable to add more songs. Playlist limit reached."
                    it.contains("Resource not found") ->
                        "Unable to add song. Please try again later."
                    else -> it
                }
                Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
            }
        }
    }

}