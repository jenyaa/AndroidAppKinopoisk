package ru.otus.kinopoisk.details

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailsData(val details: String, val imageFilm: Int) : Parcelable {

}