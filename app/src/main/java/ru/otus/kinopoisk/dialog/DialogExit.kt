package ru.otus.kinopoisk.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import ru.otus.kinopoisk.R

class DialogExit(context: Context, private val activity: Activity) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_back)
        val buttonTrue = findViewById<View>(R.id.buttonTrue)
        val buttonFalse = findViewById<View>(R.id.buttonFalse)
        //кнопка выхода
        buttonTrue.setOnClickListener{
            activity.finish()
        }

        //кнопка отмены
        buttonFalse.setOnClickListener {
            dismiss()
        }
    }
}