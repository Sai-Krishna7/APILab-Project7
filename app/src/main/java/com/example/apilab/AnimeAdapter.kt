package com.example.apilab

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class AnimeAdapter (private val animeList: List<String>, private val animeTitleList: List<String>, private val animeScoreList: List<String>) : RecyclerView.Adapter<AnimeAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val animeImage: ImageView
        val animeTitle: TextView
        val animeScore: TextView
        init {
            // Find our RecyclerView item's ImageView for future use
            animeImage = view.findViewById(R.id.anime_image)
            animeTitle = view.findViewById(R.id.AnimeTitle)
            animeScore = view.findViewById(R.id.AnimeScore)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.anime_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(animeList[position])
            .centerCrop()
            .into(holder.animeImage)
        holder.animeTitle.setText("Anime Title: " + animeTitleList[position])
        holder.animeScore.setText("Anime Score: " + animeScoreList[position])
    }

    override fun getItemCount() = animeList.size
}