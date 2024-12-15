package com.example.jozmusik.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jozmusik.Data.Entity.PlaylistSong
import com.example.jozmusik.R
import com.example.jozmusik.databinding.ItemPlaylistBinding

class PlaylistAdapter(private val onRemoveClick: (PlaylistSong) -> Unit) :
    RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder>() {

    private var songs = listOf<PlaylistSong>()

    class PlaylistViewHolder(private val binding: ItemPlaylistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(song: PlaylistSong, onRemoveClick: (PlaylistSong) -> Unit) {
            binding.apply {
                // Set teks judul dan artis
                tvTitle.text = song.title
                tvArtist.text = song.artist

                // Set gambar dari drawable lokal (item_musik)
                ivSong.setImageResource(R.drawable.item_musik)

                // Set listener untuk tombol remove
                btnRemoveFromPlaylist.setOnClickListener {
                    onRemoveClick(song)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        return PlaylistViewHolder(
            ItemPlaylistBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.bind(songs[position], onRemoveClick)
    }

    override fun getItemCount() = songs.size

    fun submitList(newList: List<PlaylistSong>) {
        songs = newList
        notifyDataSetChanged()
    }
}
