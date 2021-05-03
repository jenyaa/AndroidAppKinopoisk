package ru.otus.kinopoisk.details

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import ru.otus.kinopoisk.R

class DetailsActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_DATA = "EXTRA_DATA"
        const val EXTRA_DATA_RESULT = "EXTRA_DATA_RESULT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        //получение данных с MainActivity
        intent.getParcelableExtra<DetailsData>(EXTRA_DATA)?.let {
            findViewById<TextView>(R.id.textDetails).text = "${it.details}"
            findViewById<ImageView>(R.id.imageDetails).setImageResource(it.imageFilm)
        }

        //кнопка отправления результатов чекбокса и комментария
        findViewById<View>(R.id.buttonSend).setOnClickListener {
            setResult(Activity.RESULT_OK, Intent().apply {
                putExtra(
                    EXTRA_DATA_RESULT,
                    DetailsResultData(
                        findViewById<CheckBox>(R.id.checkBox).isChecked, findViewById<EditText>(
                            R.id.editText
                        ).text.toString()
                    )
                )
            })
            finish()
        }
    }

}