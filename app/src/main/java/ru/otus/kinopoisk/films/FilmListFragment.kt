package ru.otus.kinopoisk.films

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.otus.kinopoisk.R
import ru.otus.kinopoisk.data.DataItems

class FilmListFragment : Fragment() {
    companion object {
        const val FILM_LIST_FRAGMENT = "FilmListFragment"
    }

    var listener: FilmAdapter.FilmClickListener? = null
    lateinit var recyclerView: RecyclerView


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is onClickListener) {
            var filmlistener = context as onClickListener
            listener = object : FilmAdapter.FilmClickListener, onClickListener {
                override fun onDetailsClick(filmItem: FilmItem) {
                    filmlistener.onDetailsClick(filmItem)
                    filmItem.isSelected = true
                    recyclerView.adapter?.notifyDataSetChanged()
                }

                override fun onFavoriteClick(filmItem: FilmItem) {
                    if (filmItem.isFavorite) {
                        filmItem.isFavorite = false
                        DataItems.favoriteItems.remove(filmItem)
                    } else {
                        filmItem.isFavorite = true
                        DataItems.favoriteItems.add(filmItem)
                    }

                    filmlistener.onFavoriteClick(filmItem)
                    recyclerView.adapter?.notifyDataSetChanged()
                }

            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list_films, container, false)
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val orientation = resources.configuration.orientation

        recyclerView = view.findViewById(R.id.recyclerView1)

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            val layoutManager =
                LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
            recyclerView.layoutManager = layoutManager
        } else {
            val layoutManager =
                GridLayoutManager(view.context, 2, LinearLayoutManager.VERTICAL, false)
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
        recyclerView.adapter = FilmAdapter(DataItems.items, listener)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface onClickListener {
        fun onDetailsClick(filmItem: FilmItem)
        fun onFavoriteClick(filmItem: FilmItem)
    }
}