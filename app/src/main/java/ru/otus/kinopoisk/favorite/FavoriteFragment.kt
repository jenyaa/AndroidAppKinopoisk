package ru.otus.kinopoisk.favorite

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

class FavoriteFragment : Fragment() {

    companion object {
        const val FAVORITE_LIST_FRAGMENT = "FavoriteListFragment"
    }

    private lateinit var recyclerViewFavorite: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val orientation = resources.configuration.orientation

        recyclerViewFavorite = view.findViewById(R.id.favoriteRecyclerView1)

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            val layoutManager =
                LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
            recyclerViewFavorite.layoutManager = layoutManager
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
            recyclerViewFavorite.layoutManager = layoutManager

        }
        recyclerViewFavorite.adapter =
            FavoriteAdapter(LayoutInflater.from(requireContext()), DataItems.favoriteItems)
    }

}