package ru.otus.kinopoisk.details

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailsResultData(val like: Boolean, val comment: String) : Parcelable {

}