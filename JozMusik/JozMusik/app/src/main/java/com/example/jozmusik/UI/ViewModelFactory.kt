package com.example.jozmusik.UI

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jozmusik.Data.Dao.PlaylistDao
import com.example.jozmusik.Data.Preferences.PreferenceManager
import com.example.jozmusik.Data.Repository.PlaylistRepository
import com.example.jozmusik.Data.Repository.SongRepository
import com.example.jozmusik.UI.Home.HomeViewModel
import com.example.jozmusik.UI.Playlist.PlaylistViewModel

class ViewModelFactory(
    private val repository: Any,
    private val prefManager: PreferenceManager,
    private val playlistDao: PlaylistDao
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository as SongRepository, prefManager, playlistDao) as T
            }
            modelClass.isAssignableFrom(PlaylistViewModel::class.java) -> {
                // PlaylistViewModel hanya membutuhkan repository
                PlaylistViewModel(repository as PlaylistRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}