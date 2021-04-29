package ru.otus.kinopoisk.favorite

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.otus.kinopoisk.R
import ru.otus.kinopoisk.films.FilmItem

class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val favoriteImageFilm: ImageView = itemView.findViewById(R.id.favoriteImageFilm)
    val favoriteTitleFilm: TextView = itemView.findViewById(R.id.favoriteTitleFilm)
    val favoriteImageDelete: ImageView = itemView.findViewById(R.id.favoriteImageDelete)

    fun bind(item: FilmItem) {
        favoriteImageFilm.setImageResource(item.imageFilm)
        favoriteTitleFilm.text = item.titleFilm
        favoriteImageDelete.setImageResource(item.favoriteFilm)
    }

}