package ru.otus.kinopoisk

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_TEXT_DPS = "EXTRA_TEXT_DPS"
        const val EXTRA_TEXT_WWS = "EXTRA_TEXT_WWS"
        const val EXTRA_TEXT_SLH = "EXTRA_TEXT_SLH"
        const val REQUEST_CODE = 1
    }

    private val textViewDeadPoetsSociety by lazy {
        findViewById<TextView>(R.id.titleDeadPoetsSociety)
    }

    private val textViewTheWolfOfWallStreet by lazy {
        findViewById<TextView>(R.id.titleTheWolfOfWallStreet)
    }

    private val textViewSomeLikeHot by lazy {
        findViewById<TextView>(R.id.titleSomeLikeHot)
    }

    val detailsDeadPoetsSociety =
        "Джон Китинг — новый преподаватель английской словесности в консервативном " +
                "американском колледже. От чопорной массы учителей его выгодно отличают легкость общения, " +
                "эксцентричное поведение и пренебрежение к программе обучения."

    val detailsTheWolfOfWallStreet =
        "1987 год. Джордан Белфорт становится брокером в успешном инвестиционном банке. " +
                "Вскоре банк закрывается после внезапного обвала индекса Доу-Джонса. " +
                "По совету жены Терезы Джордан устраивается в небольшое заведение, занимающееся мелкими акциями. " +
                "Его настойчивый стиль общения с клиентами и врождённая харизма быстро даёт свои плоды."

    val detailsSomeLikeHot =
        "Когда чикагские музыканты Джо и Джерри случайно становятся свидетелями бандитской перестрелки," +
                " они в срочном порядке смываются на поезде во Флориду, прикинувшись женщинами. " +
                "Теперь они - Джозефина и Дафна, новенькие и хорошенькие инструменталистки женского джаз-бэнда."

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Кнопка "детали" к фильму "Общество мертвых поэтов"
        findViewById<View>(R.id.buttonDeadPoetsSociety).setOnClickListener {
            textViewDeadPoetsSociety.setTextColor(
                ContextCompat.getColor(
                    applicationContext,
                    R.color.colorPrimary
                )
            )
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra(
                DetailsActivity.EXTRA_DATA,
                FilmData(detailsDeadPoetsSociety, R.drawable.deadpoetssociety)
            )
            startActivityForResult(intent, REQUEST_CODE)
        }

        // Кнопка "детали" к фильму "Волк с Уолл-Стрит"
        findViewById<View>(R.id.buttonTheWolfOfWallStreet).setOnClickListener {
            textViewTheWolfOfWallStreet.setTextColor(
                ContextCompat.getColor(
                    applicationContext,
                    R.color.colorPrimary
                )
            )
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra(
                DetailsActivity.EXTRA_DATA,
                FilmData(detailsTheWolfOfWallStreet, R.drawable.thewolfofwallstreet)
            )
            startActivityForResult(intent, REQUEST_CODE)
        }

        // Кнопка "детали" к фильму "В джазе только девушки"
        findViewById<View>(R.id.buttonSomeLikeHot).setOnClickListener {
            textViewSomeLikeHot.setTextColor(
                ContextCompat.getColor(
                    applicationContext,
                    R.color.colorPrimary
                )
            )
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra(
                DetailsActivity.EXTRA_DATA,
                FilmData(detailsSomeLikeHot, R.drawable.somelikehot)
            )
            startActivityForResult(intent, REQUEST_CODE)
        }

        //Кнопка "пригласить друга"
        findViewById<View>(R.id.buttonInvite).setOnClickListener {
            val share = Intent.createChooser(Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT,
                    "Hey! this is my app https://play.google.com/store/apps/details?id=$packageName"
                )
            }, null)
            startActivity(share)
        }

        savedInstanceState?.getInt(EXTRA_TEXT_DPS)?.let {
            textViewDeadPoetsSociety.setTextColor(it)
        }
        savedInstanceState?.getInt(EXTRA_TEXT_WWS)?.let {
            textViewTheWolfOfWallStreet.setTextColor(it)
        }
        savedInstanceState?.getInt(EXTRA_TEXT_SLH)?.let {
            textViewSomeLikeHot.setTextColor(it)
        }

    }

    //сохранение цветов названия фильмов при перевороте экрана
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(EXTRA_TEXT_DPS, textViewDeadPoetsSociety.currentTextColor)
        outState.putInt(EXTRA_TEXT_WWS, textViewTheWolfOfWallStreet.currentTextColor)
        outState.putInt(EXTRA_TEXT_SLH, textViewSomeLikeHot.currentTextColor)
    }

    //возвращение результатов чекбокса и комментария
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val resultData =
                    data?.getParcelableExtra<ResultData>(DetailsActivity.EXTRA_DATA_RESULT)
                resultData?.let {
                    Toast.makeText(this, "checkbox: ${it.like} ; ${it.comment}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

}
