package com.example.jozmusik.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jozmusik.Data.Entity.Song
import com.example.jozmusik.R
import com.example.jozmusik.databinding.ItemSongBinding

class SongAdapter(private val onAddClick: (Song) -> Unit) :
    RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    private var songs = listOf<Song>()

    class SongViewHolder(private val binding: ItemSongBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(song: Song, onAddClick: (Song) -> Unit) {
            binding.apply {
                tvTitle.text = song.title
                tvArtist.text = song.artist

                // Load image from drawable instead of using Glide
                ivSong.setImageResource(R.drawable.item_musik)

                btnAddToPlaylist.setOnClickListener {
                    onAddClick(song)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        return SongViewHolder(
            ItemSongBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        holder.bind(songs[position], onAddClick)
    }

    override fun getItemCount() = songs.size

    fun submitList(newList: List<Song>) {
        songs = newList
        notifyDataSetChanged()
    }
}
