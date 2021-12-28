package com.mjh.mathseducationapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.Toast
import com.mjh.mathseducationapplication.model.Answer
import com.mjh.mathseducationapplication.model.table.AnswerTable
import com.mjh.mathseducationapplication.model.util.AnswerAdapter

/**
 * A class representing the [ViewAnswersActivity] controller.
 *
 * This class provides the functionality of the [ViewAnswersActivity] such as setting the view and
 * button functionality.
 */
class ViewAnswersActivity : AppCompatActivity() {
    private lateinit var answerTable: AnswerTable
    private lateinit var adapter: AnswerAdapter
    private var selectedAnswer: Answer? = null

    /*---- Methods ----*/
    /**
     * Allows the admin to remove a selected answer from the Answer Table.
     *
     * @param view The area responsible for drawing and event handling.
     */
    fun removeAnswer(view: View) {
        // Check if the Admin has selected an answer.
        if(this.selectedAnswer != null) {
            this.answerTable.removeAnswer(this.selectedAnswer!!)

            // Update the list view.
            this.adapter.updateList(this.answerTable.getAnswers())
            this.adapter.notifyDataSetChanged()

            this.selectedAnswer = null
        } else {
            Toast.makeText(this, "You must select an answer to delete!", Toast.LENGTH_SHORT).show()
        }
    }

    /*---- Overridden Methods ----*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_answers)

        this.answerTable = AnswerTable(this, "MathsEducation.db", 1)

        val answers: List<Answer> = this.answerTable.getAnswers()

        this.adapter = AnswerAdapter(applicationContext, answers)
        findViewById<ListView>(R.id.listViewAnswers).adapter = this.adapter

        // On Click Listener which gets the clicked on Answer.
        findViewById<ListView>(R.id.listViewAnswers).setOnItemClickListener { parent, view, position, id ->
            this.selectedAnswer = answers[position]
        }
    }
}