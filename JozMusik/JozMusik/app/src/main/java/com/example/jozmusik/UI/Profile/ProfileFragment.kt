package com.example.jozmusik.UI.Profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.jozmusik.Data.Preferences.PreferenceManager
import com.example.jozmusik.UI.Auth.LoginActivity
import com.example.jozmusik.databinding.FragmentProfileBinding

// UI/Profile/ProfileFragment.kt
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var prefManager: PreferenceManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefManager = PreferenceManager(requireContext())
        setupViews()
    }

    private fun setupViews() {
        binding.apply {
            tvFullName.text = prefManager.getUsername()

            btnLogout.setOnClickListener {
                prefManager.clearUser()
                startActivity(Intent(requireContext(), LoginActivity::class.java))
                requireActivity().finish()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}