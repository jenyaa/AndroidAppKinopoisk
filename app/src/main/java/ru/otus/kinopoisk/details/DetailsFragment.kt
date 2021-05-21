package ru.otus.kinopoisk.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import ru.otus.kinopoisk.R

class DetailsFragment : Fragment() {

    companion object {
        const val DETAILS_FRAGMENT = "DetailsFragment"
        const val EXTRA_DATA_DES = "EXTRA_DATA_DES"
        const val EXTRA_DATA_IMAGE = "EXTRA_DATA_RESULT_IMAGE"


        //получение данных из MainActivity
        fun newInstance(des: String, image: Int): DetailsFragment {
            val args = Bundle()
            args.putString(EXTRA_DATA_DES, des)
            args.putInt(EXTRA_DATA_IMAGE, image)
            val fragment = DetailsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<TextView>(R.id.textDetails1).text =
            arguments?.getString(EXTRA_DATA_DES) ?: "default"
        view.findViewById<ImageView>(R.id.imageDetails1).setImageResource(
            arguments?.getInt(
                EXTRA_DATA_IMAGE
            ) ?: R.drawable.somelikehot
        )
    }
}