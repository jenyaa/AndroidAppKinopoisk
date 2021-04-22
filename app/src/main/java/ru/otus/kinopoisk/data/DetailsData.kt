package ru.otus.kinopoisk.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class DetailsData (val details: String, val imageFilm: Int) : Parcelable {

}