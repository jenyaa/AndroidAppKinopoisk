package ru.otus.kinopoisk.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.otus.kinopoisk.R
import ru.otus.kinopoisk.data.DataItems
import ru.otus.kinopoisk.films.FilmItem

class FavoriteAdapter(private val favoriteItems: List<FilmItem>, private val clickListener: FavoriteClickListener) :
    RecyclerView.Adapter<FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_film_favorite, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val item = favoriteItems[position]
        var idFilm = favoriteItems[position].idForFavoriteList
        holder.bind(item)

        holder.favoriteImageDelete.setOnClickListener {
            clickListener.onDeleteClick(item, idFilm)
            DataItems.favoriteItems.removeAt(position)
            DataItems.items[idFilm].isFavorite = false
            notifyDataSetChanged()
        }
    }

    override fun getItemCount() = favoriteItems.size

    interface FavoriteClickListener {
        fun onDeleteClick(filmItem: FilmItem, int: Int){

        }
    }

}

