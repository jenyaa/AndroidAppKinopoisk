package ru.otus.kinopoisk.films

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.otus.kinopoisk.R
import ru.otus.kinopoisk.data.DataItems

class FilmAdapter(private val items: List<FilmItem>, private val clickListener: FilmClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var favoritePositionAdapter: Int = 0

    companion object {
        const val VIEW_TYPE_FILM = 0
        const val VIEW_TYPE_HEADER = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return if (viewType == VIEW_TYPE_FILM) {
            val view = layoutInflater.inflate(R.layout.item_film, parent, false)
            FilmItemViewHolder(view)
        } else {
            val view = layoutInflater.inflate(R.layout.item_header, parent, false)
            FilmHeaderViewHolder(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) VIEW_TYPE_HEADER else VIEW_TYPE_FILM
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is FilmItemViewHolder) {

            val item = items[position - 1]
            holder.bind(item)

            holder.itemView.setOnClickListener {
                clickListener.onDetailsClick(item)
            }

            //кнопка детали
            holder.buttonDetail.setOnClickListener {
                clickListener.onDetailsClick(item)
            }

            //изменение изображения и цвета названия фильма
            if (item.isFavorite) {
                holder.imageFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
                holder.titleFilm.setTextColor(Color.YELLOW)
            } else {
                holder.imageFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                holder.titleFilm.setTextColor(Color.GRAY)
            }

            //кнопка сердечко рядом с фильмом
            holder.imageFavorite.setOnClickListener {
                favoritePositionAdapter = position
                if (!items[favoritePositionAdapter - 1].isFavorite) {
                    clickListener.onFavoriteClick(item, favoritePositionAdapter)
                    items[favoritePositionAdapter - 1].isFavorite = true
                    DataItems.favoriteItems.add(item)
                    notifyDataSetChanged()
                }
            }


        } else if (holder is FilmHeaderViewHolder) {

            //кнопка пригласить друга
            holder.buttonInvite.setOnClickListener {
                clickListener.onInviteClick()
            }

            //кнопка перехода в список избранного
            holder.buttonFavoriteList.setOnClickListener {
                clickListener.onFavoriteListClick(favoritePositionAdapter)
            }
        }
    }

    override fun getItemCount() = items.size + 1 //+1 header

    interface FilmClickListener {
        fun onDetailsClick(filmItem: FilmItem) {}

        fun onFavoriteClick(item: FilmItem, itemFav: Int) {}

        fun onFavoriteListClick(itemFav: Int) {}

        fun onInviteClick() {}
    }
}