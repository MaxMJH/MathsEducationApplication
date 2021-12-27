package com.mjh.mathseducationapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class AdminSectionActivity : AppCompatActivity() {
    /*---- Methods ----*/
    fun addQuestion(view: View) {
        val intent: Intent = Intent(this, AddQuestionActivity::class.java)
        startActivity(intent)
    }

    fun viewQuestionsWithAnswers(view: View) {
        val intent: Intent = Intent(this, ViewQuestionsWithAnswersActivity::class.java)
        startActivity(intent)
    }

    fun addAnswer(view: View) {
        val intent: Intent = Intent(this, AddAnswerActivity::class.java)
        startActivity(intent)
    }

    fun viewAnswers(view: View) {
        val intent: Intent = Intent(this, ViewAnswersActivity::class.java)
        startActivity(intent)
    }

    fun addAdmin(view: View) {
        val intent: Intent = Intent(this, UserActivity::class.java)
        startActivity(intent)
    }

    fun viewAdmins(view: View) {
        val intent: Intent = Intent(this, ViewAdminsActivity::class.java)
        startActivity(intent)
    }

    /*---- Overridden Methods ----*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_section)
    }
}