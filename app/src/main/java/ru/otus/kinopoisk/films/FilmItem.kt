package ru.otus.kinopoisk.films

data class FilmItem(
    val imageFilm: Int, val titleFilm: String,
    val descriptionFilm: String, var favoriteFilm: Int,
    var isFavorite: Boolean, var idForFavoriteList: Int, var isSelected: Boolean
) {

}