package ru.otus.kinopoisk

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import ru.otus.kinopoisk.data.DataItems
import ru.otus.kinopoisk.details.DetailsFragment
import ru.otus.kinopoisk.dialog.DialogExit
import ru.otus.kinopoisk.favorite.FavoriteFragment
import ru.otus.kinopoisk.films.FilmItem
import ru.otus.kinopoisk.films.FilmListFragment

class MainActivity : AppCompatActivity(), FilmListFragment.onClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openListFilm()

        //навигация между фрагментами
        navigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_film -> {
                    openListFilm()

                    return@setOnNavigationItemSelectedListener true
                }

                R.id.navigation_favorite -> {
                    openFavoriteList()
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.navigation_invite_friend -> {
                    onInviteClick()
                    return@setOnNavigationItemSelectedListener true
                }

            }
            false
        }
    }

    //открыть FilmListFragment
    private fun openListFilm() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.containerFilm, FilmListFragment(), FilmListFragment.FILM_LIST_FRAGMENT)
            .commit()
    }

    //открыть DetailsFragment
    private fun openDetails(filmItem: FilmItem) {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.containerFilm,
                DetailsFragment.newInstance(filmItem.descriptionFilm, filmItem.imageFilm),
                DetailsFragment.DETAILS_FRAGMENT
            )
            .addToBackStack(null)
            .commit()
    }

    //открыть FavoriteFragment
    private fun openFavoriteList() {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.containerFilm,
                FavoriteFragment(),
                FavoriteFragment.FAVORITE_LIST_FRAGMENT
            )
            .addToBackStack(null)
            .commit()
    }

    //кнопка "Детали" вызывается при нажатии на item
    override fun onDetailsClick(filmItem: FilmItem) {
        openDetails(filmItem)
    }

    //кнопка "Добавить в избранное" + snackbar с возможностью отмены действия
    override fun onFavoriteClick(filmItem: FilmItem) {
        var snackbarTitle: String = filmItem.titleFilm
        var snackbarFavorite: String
        val view = findViewById<View>(R.id.viewMainActivity)

        if (filmItem.isFavorite) {
            snackbarFavorite = " добавлен в избранное"
        } else {
            snackbarFavorite = " удален из избранного"
        }

        Snackbar.make(view, "$snackbarTitle $snackbarFavorite", Snackbar.LENGTH_LONG)
            .setAction("Отменить") {
                if (filmItem.isFavorite) {
                    filmItem.isFavorite = false
                    DataItems.favoriteItems.remove(filmItem)
                } else {
                    filmItem.isFavorite = true
                    DataItems.favoriteItems.add(filmItem)
                }
                openListFilm()
            }.show()
    }


    //кнопка "пригласить друга"
    private fun onInviteClick() {
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

    //кнопка выхода из приложения
    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            super.onBackPressed()
        } else {
            val dialog: Dialog = DialogExit(this, this)
            dialog.show()
        }
    }
}