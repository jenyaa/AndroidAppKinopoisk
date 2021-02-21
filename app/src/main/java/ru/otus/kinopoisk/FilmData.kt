package ru.otus.kinopoisk

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class FilmData (val details: String, val imageFilm: Int) : Parcelable {

}