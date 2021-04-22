package ru.otus.kinopoisk.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class FavoriteData (val imageFav: Int, val titleFav: String, val idFav: Int) : Parcelable {
}