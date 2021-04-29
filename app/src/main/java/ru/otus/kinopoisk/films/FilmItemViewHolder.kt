package ru.otus.kinopoisk.films

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.otus.kinopoisk.R
import ru.otus.kinopoisk.films.FilmItem

class FilmItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val imageFilm : ImageView = itemView.findViewById(R.id.imageFilm)
    val titleFilm: TextView = itemView.findViewById(R.id.titleFilm)
    val buttonDetail: View = itemView.findViewById(R.id.buttonDetails)
    val imageFavorite: ImageView = itemView.findViewById(R.id.imageFavorite)

    fun bind (item: FilmItem) {
        imageFilm.setImageResource(item.imageFilm)
        titleFilm.text = item.titleFilm
        imageFavorite.setImageResource(item.favoriteFilm)
    }

}