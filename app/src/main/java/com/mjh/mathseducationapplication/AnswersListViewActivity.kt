package com.mjh.mathseducationapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/**
 * A class representing the [AnswersListViewActivity] controller.
 *
 * This class contains the list view that is used in [ViewAnswersActivity]
 */
class AnswersListViewActivity : AppCompatActivity() {
    /*---- Overridden Methods ----*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answers_list_view)
    }
}