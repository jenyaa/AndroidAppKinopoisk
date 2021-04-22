package ru.otus.kinopoisk.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import ru.otus.kinopoisk.MainActivity
import ru.otus.kinopoisk.R
import ru.otus.kinopoisk.data.FavoriteData
import ru.otus.kinopoisk.data.FavoriteResultData

class FavoriteActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_DATA_FAVORITE = "EXTRA_DATA_FAVORITE"
        const val EXTRA_DATA_FAVORITE_RESULT = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        var favoritePosition = 0

        //получение данных из MainActivity (FavoriteData)
        if (intent != null) {
            intent.getParcelableExtra<FavoriteData>(EXTRA_DATA_FAVORITE)?.let {
                findViewById<ImageView>(R.id.imageFilmFavorite).setImageResource(it.imageFav)
                findViewById<TextView>(R.id.titleFilmFavorite).text = "${it.titleFav}"
                favoritePosition = it.idFav
            }
        }

        // Передача данных в MainActivity (FavoriteResultData)
        findViewById<View>(R.id.deleteFilmFavorite).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(
                MainActivity.EXTRA_DELETE_ID_FAVORITE, FavoriteResultData(favoritePosition)
            )
            startActivityForResult(intent, EXTRA_DATA_FAVORITE_RESULT)
        }

    }


}