package ru.otus.kinopoisk.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class DetailsResultData(val like: Boolean, val comment: String) : Parcelable {

}