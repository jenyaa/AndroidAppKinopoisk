package ru.otus.kinopoisk.films

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.otus.kinopoisk.R

class FilmItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val imageFilm: ImageView = itemView.findViewById(R.id.imageFilm)
    val titleFilm: TextView = itemView.findViewById(R.id.titleFilm)
    val imageFavorite: ImageView = itemView.findViewById(R.id.imageFavorite)

    fun bind(item: FilmItem) {
        imageFilm.setImageResource(item.imageFilm)
        titleFilm.text = item.titleFilm

        if (item.isFavorite) {
            imageFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
        } else {
            imageFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }

        if (item.isSelected) {
            titleFilm.setTextColor(Color.YELLOW)
        } else {
            titleFilm.setTextColor(Color.GRAY)
        }
    }

}