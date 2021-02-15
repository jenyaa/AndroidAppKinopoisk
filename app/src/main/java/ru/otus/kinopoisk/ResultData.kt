package ru.otus.kinopoisk

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ResultData(val like: Boolean, val comment: String) : Parcelable {

}