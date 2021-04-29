package ru.otus.kinopoisk

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.otus.kinopoisk.data.DataItems
import ru.otus.kinopoisk.details.DetailsActivity
import ru.otus.kinopoisk.favorite.FavoriteActivity
import ru.otus.kinopoisk.details.DetailsData
import ru.otus.kinopoisk.details.DetailsResultData
import ru.otus.kinopoisk.dialog.DialogExit
import ru.otus.kinopoisk.favorite.FavoriteResultData
import ru.otus.kinopoisk.films.FilmAdapter
import ru.otus.kinopoisk.films.FilmItem

class MainActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID_FAVORITE = "EXTRA_ID_FAVORITE"
        const val EXTRA_DELETE_ID_FAVORITE = "EXTRA_DELETE_ID_FAVORITE"
        const val REQUEST_CODE = 1
    }

    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.recyclerView) }
    var items: MutableList<FilmItem> = mutableListOf()
    var favoriteItems: MutableList<FilmItem> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        items = DataItems.items
        favoriteItems = DataItems.favoriteItems

        initRecyclerView()


        //получение данных при повороте экрана
        savedInstanceState?.getInt(EXTRA_ID_FAVORITE)?.let {
            DataItems.checkFavoriteItem = it
            DataItems.favoriteId = it
            items[DataItems.favoriteId].isFavorite = true
            favoriteItems.add(0, items[DataItems.favoriteId])
            recyclerView.adapter?.notifyDataSetChanged()

        }

        //получение данных из FavoriteActivity
        intent.getParcelableExtra<FavoriteResultData>(EXTRA_DELETE_ID_FAVORITE)?.let {
            recyclerView.adapter?.notifyDataSetChanged()
        }
    }


    //инициализации RecyclerView
    private fun initRecyclerView() {
        val orientation = resources.configuration.orientation

        //настройка отображения экрана в зависимости от ориентации
        if (orientation == ORIENTATION_PORTRAIT) {
            val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            recyclerView.layoutManager = layoutManager
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
            recyclerView.layoutManager = layoutManager
        }
        val intent = Intent(this, DetailsActivity::class.java)
        val intentFavoriteActivity = Intent(this, FavoriteActivity::class.java)
        recyclerView.adapter = FilmAdapter(items, object : FilmAdapter.FilmClickListener {

            //кнопка перехода в Details.Activity
            override fun onDetailsClick(filmItem: FilmItem) {
                intent.putExtra(
                    DetailsActivity.EXTRA_DATA,
                    DetailsData(filmItem.descriptionFilm, filmItem.imageFilm)
                )
                startActivityForResult(intent, REQUEST_CODE)
            }

            //кнопка нажатия на сердечко рядом с фильмом
            override fun onFavoriteClick(item: FilmItem, positionFavAdapter: Int) {

                if (DataItems.checkFavoriteItem != -1) {
                    items[DataItems.checkFavoriteItem].isFavorite =
                        false //устанавливает фильму значение false, если фильм был выбран при повороте экрана
                }

                DataItems.favoriteId = positionFavAdapter
            }

            //кнопка перехода в список избранного
            override fun onFavoriteListClick(positionFavAdapter: Int) {

                //переход при наличии фильмов в списке избранного
                if (DataItems.favoriteId != -1) {
                    startActivity(intentFavoriteActivity)
                }

                //переход при пустом списке избранного
                else {
                    showNullFavoriteDialog()
                }
            }

            //кнопка "пригласить друга"
            override fun onInviteClick() {
                val share = Intent.createChooser(Intent().apply {
                    action = Intent.ACTION_SEND
                    type = "text/plain"
                    putExtra(
                        Intent.EXTRA_TEXT,
                        "Hey! this is my app https://play.google.com/store/apps/details?id=$packageName"
                    )
                }, null)
                startActivity(share)
            }
        })
    }


    //сохранение id выбранного фильма при перевороте экрана
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        var checkList = 0
        while (checkList < items.size) {
            if (items[checkList].isFavorite) {
                outState.putInt(EXTRA_ID_FAVORITE, checkList)
            }
            checkList++
        }
    }

    //возвращение результатов чекбокса и комментария из DetailsActivity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val resultData =
                    data?.getParcelableExtra<DetailsResultData>(DetailsActivity.EXTRA_DATA_RESULT)
                resultData?.let {
                    Toast.makeText(this, "checkbox: ${it.like} ; ${it.comment}", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    //диалог при выходе из приложения
    override fun onBackPressed() {
        val dialog: Dialog = DialogExit(this, this)
        dialog.show()
    }

    //диалог при переходе в пустой список избранного
    fun showNullFavoriteDialog() {
        val dialogFavorite: AlertDialog.Builder = AlertDialog.Builder(this)
        val dismissSend = DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() }
        dialogFavorite.setMessage("В списке избранного ничего нет:(")
        dialogFavorite.setNeutralButton("OK", dismissSend)
        val dialog: AlertDialog = dialogFavorite.create()
        dialog.show()
    }

}

