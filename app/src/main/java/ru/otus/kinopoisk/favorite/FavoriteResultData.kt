package ru.otus.kinopoisk.favorite

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import ru.otus.kinopoisk.films.FilmItem
@Parcelize
data class FavoriteResultData (val updateItem: Int):Parcelable