package ru.otus.kinopoisk.films

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.otus.kinopoisk.R

class FilmAdapter(
    private val items: List<FilmItem>,
    private val listener: FilmClickListener?
) : RecyclerView.Adapter<FilmItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_film, parent, false)
        return FilmItemViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: FilmItemViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)

        holder.itemView.setOnClickListener {
            listener?.onDetailsClick(item)
        }

        holder.imageFavorite.setOnClickListener {
            listener?.onFavoriteClick(item)
        }
    }

    interface FilmClickListener {
        fun onDetailsClick(filmItem: FilmItem) {}
        fun onFavoriteClick(filmItem: FilmItem) {}
    }
}