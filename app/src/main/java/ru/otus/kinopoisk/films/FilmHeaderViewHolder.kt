package ru.otus.kinopoisk.films

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.otus.kinopoisk.R

class FilmHeaderViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    val buttonInvite : View = itemView.findViewById(R.id.buttonInvite)
    val buttonFavoriteList: View = itemView.findViewById(R.id.buttonFavoriteList)
}