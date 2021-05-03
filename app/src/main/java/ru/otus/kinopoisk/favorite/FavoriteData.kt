package ru.otus.kinopoisk.favorite

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FavoriteData( val idFav: Int) : Parcelable {
}