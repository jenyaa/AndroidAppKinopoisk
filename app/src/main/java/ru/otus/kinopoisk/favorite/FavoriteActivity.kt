package ru.otus.kinopoisk.favorite

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.otus.kinopoisk.MainActivity
import ru.otus.kinopoisk.MainActivity.Companion.EXTRA_DELETE_ID_FAVORITE
import ru.otus.kinopoisk.R
import ru.otus.kinopoisk.data.DataItems
import ru.otus.kinopoisk.films.FilmItem

class FavoriteActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_DATA_FAVORITE_RESULT = 1
    }

    private lateinit var favoriteRV: RecyclerView
    private lateinit var updateItems: List<FilmItem>
    private var updateItem = DataItems.updateId


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        favoriteRV = findViewById(R.id.favoriteRecyclerView)
        updateItems = DataItems.items
        initFavoriteRecyclerView()
    }


    fun initFavoriteRecyclerView() {
        val orientation = resources.configuration.orientation

        //настройка отображения экрана в зависимости от ориентации
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            favoriteRV.layoutManager = layoutManager
        } else {
            val layoutManager = GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false)
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int) =
                    if (position > 0) {
                        1
                    } else {
                        2
                    }
            }
            favoriteRV.layoutManager = layoutManager
        }
        favoriteRV.adapter = FavoriteAdapter(
            DataItems.favoriteItems,
            object : FavoriteAdapter.FavoriteClickListener {
                override fun onDeleteClick(filmItem: FilmItem, id: Int) {
                    super.onDeleteClick(filmItem, id)
                    updateItem = id
                }
            }
        )
    }

    //возвращение в MainActivity
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        intent.putExtra(
            EXTRA_DELETE_ID_FAVORITE, FavoriteResultData(updateItem)
        )
        startActivityForResult(intent, EXTRA_DATA_FAVORITE_RESULT)
    }

}


