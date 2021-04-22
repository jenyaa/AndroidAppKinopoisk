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
import ru.otus.kinopoisk.activity.DetailsActivity
import ru.otus.kinopoisk.activity.FavoriteActivity
import ru.otus.kinopoisk.data.FavoriteData
import ru.otus.kinopoisk.data.FavoriteResultData
import ru.otus.kinopoisk.data.DetailsData
import ru.otus.kinopoisk.data.DetailsResultData
import ru.otus.kinopoisk.dialog.CustomDialog
import ru.otus.kinopoisk.recyclerView.FilmAdapter
import ru.otus.kinopoisk.recyclerView.FilmItem

class MainActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID_FAVORITE = "EXTRA_ID_FAVORITE"
        const val EXTRA_DELETE_ID_FAVORITE = "EXTRA_DELETE_ID_FAVORITE"
        const val REQUEST_CODE = 1
        const val REQUEST_CODE_FAVORITE = 1
    }

    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.recyclerView) }
    private val items: MutableList<FilmItem> = mutableListOf()
    private var favoriteItems: MutableList<FilmItem> = mutableListOf()
    var favoriteId: Int = -1
    var checkFavoriteItem: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()
        initItemsList()


        //получение данных при повороте экрана
        savedInstanceState?.getInt(EXTRA_ID_FAVORITE)?.let {
            checkFavoriteItem = it
            favoriteId = it
            items[favoriteId].isFavorite = true
            favoriteItems.add(0, items[favoriteId])
        }

        //получение данных из FavoriteActivity
        intent.getParcelableExtra<FavoriteResultData>(EXTRA_DELETE_ID_FAVORITE)?.let {
            items[it.idFavoriteFilm].isFavorite = false
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

                if (checkFavoriteItem != -1) {
                    items[checkFavoriteItem].isFavorite =
                        false //устанавливает фильму значение false, если фильм был выбран при повороте экрана
                }

                favoriteItems.add(0, item)
                favoriteId = positionFavAdapter
            }

            //кнопка перехода в список избранного
            override fun onFavoriteListClick(positionFavAdapter: Int) {

                //переход при наличии фильмов в списке избранного
                if (favoriteId != -1) {
                    intentFavoriteActivity.putExtra(
                        FavoriteActivity.EXTRA_DATA_FAVORITE,
                        FavoriteData(
                            favoriteItems[0].imageFilm,
                            favoriteItems[0].titleFilm,
                            positionFavAdapter
                        )
                    )
                    startActivityForResult(intentFavoriteActivity, REQUEST_CODE_FAVORITE)
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
        val dialog: Dialog = CustomDialog(this, this)
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

    //инициализация списка фильмов
    private fun initItemsList() {
        items.add(
            FilmItem(
                R.drawable.somelikehot, resources.getString(R.string.title_film_somelikehot),
                resources.getString(R.string.description_film_somelikehot), 0, false
            )
        )

        items.add(
            FilmItem(
                R.drawable.deadpoetssociety,
                resources.getString(R.string.title_film_deadpoetssociety),
                resources.getString(R.string.description_film_deadpoetssociety),
                0,
                false
            )
        )

        items.add(
            FilmItem(
                R.drawable.thewolfofwallstreet,
                resources.getString(R.string.title_film_thewolfofwallstreet),
                resources.getString(R.string.description_film_thewolfofwallstreet),
                0,
                false
            )
        )

        items.add(
            FilmItem(
                R.drawable.piratesofthecaribbean,
                resources.getString(R.string.title_film_piratesofthecaribbean),
                resources.getString(R.string.description_film_piratesofthecaribbean),
                0,
                false
            )
        )

        items.add(
            FilmItem(
                R.drawable.theboyinthestripedpyjamas,
                resources.getString(R.string.title_film_theboyinthestripedpyjamas),
                resources.getString(R.string.description_film_theboyinthestripedpyjamas),
                0,
                false
            )
        )

        items.add(
            FilmItem(
                R.drawable.thehobbit, resources.getString(R.string.title_film_thehobbit),
                resources.getString(R.string.description_thehobbit), 0, false
            )
        )

        items.add(
            FilmItem(
                R.drawable.knockinonheavensdoor,
                resources.getString(R.string.title_film_knockinonheavensdoor),
                resources.getString(R.string.description_film_knockinonheavensdoor),
                0,
                false
            )
        )

        items.add(
            FilmItem(
                R.drawable.thegreenmile, resources.getString(R.string.title_film_thegreenmile),
                resources.getString(R.string.description_film_thegreenmile), 0, false
            )
        )

        items.add(
            FilmItem(
                R.drawable.goodwillhunting,
                resources.getString(R.string.title_film_goodwillhunting),
                resources.getString(R.string.description_film_goodwillhunting),
                0,
                false
            )
        )

        items.add(
            FilmItem(
                R.drawable.lifeisbeautiful,
                resources.getString(R.string.title_film_lifeisbeautiful),
                resources.getString(R.string.description_film_lifeisbeautiful),
                0,
                false
            )
        )
    }
}





