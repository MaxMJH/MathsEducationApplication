package com.mjh.mathseducationapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

/**
 * A class representing the [AdminSectionActivity] controller.
 *
 * This class provides the functionality of the [AdminSectionActivity] such as setting the view and
 * button functionality.
 */
class AdminSectionActivity : AppCompatActivity() {
    /*---- Methods ----*/
    /**
     * Sends the Admin to the [AddQuestionActivity]
     *
     * @param view The area responsible for drawing and event handling.
     */
    fun addQuestion(view: View) {
        val addQuestionIntent: Intent = Intent(this, AddQuestionActivity::class.java).apply {
            putExtra("admin", intent.getSerializableExtra("admin"))
        }
        startActivity(addQuestionIntent)
    }

    /**
     * Sends the Admin to the [ViewQuestionsWithAnswersActivity]
     *
     * @param view The area responsible for drawing and event handling.
     */
    fun viewQuestionsWithAnswers(view: View) {
        val viewQuestionsWithAnswerIntent: Intent = Intent(this, ViewQuestionsWithAnswersActivity::class.java).apply {
            putExtra("admin", intent.getSerializableExtra("admin"))
        }
        startActivity(viewQuestionsWithAnswerIntent)
    }

    /**
     * Sends the Admin to the [AddAnswerActivity]
     *
     * @param view The area responsible for drawing and event handling.
     */
    fun addAnswer(view: View) {
        val addAnswerIntent: Intent = Intent(this, AddAnswerActivity::class.java).apply {
            putExtra("admin", intent.getSerializableExtra("admin"))
        }
        startActivity(addAnswerIntent)
    }

    /**
     * Sends the Admin to the [ViewAnswersActivity]
     *
     * @param view The area responsible for drawing and event handling.
     */
    fun viewAnswers(view: View) {
        val viewAnswersIntent: Intent = Intent(this, ViewAnswersActivity::class.java).apply {
            putExtra("admin", intent.getSerializableExtra("admin"))
        }
        startActivity(viewAnswersIntent)
    }

    /**
     * Sends the Admin to the [UserActivity]
     *
     * @param view The area responsible for drawing and event handling.
     */
    fun addAdmin(view: View) {
        val addAdminIntent: Intent = Intent(this, UserActivity::class.java).apply {
            putExtra("admin", intent.getSerializableExtra("admin"))
        }
        startActivity(addAdminIntent)
    }

    /**
     * Sends the Admin to the [ViewAdminsActivity]
     *
     * @param view The area responsible for drawing and event handling.
     */
    fun viewAdmins(view: View) {
        val viewAdminsIntent: Intent = Intent(this, ViewAdminsActivity::class.java).apply {
            putExtra("admin", intent.getSerializableExtra("admin"))
        }
        startActivity(viewAdminsIntent)
    }

    /*---- Overridden Methods ----*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_section)
    }
}