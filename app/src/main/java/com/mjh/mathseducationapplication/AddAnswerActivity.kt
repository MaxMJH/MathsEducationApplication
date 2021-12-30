package com.mjh.mathseducationapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.mjh.mathseducationapplication.model.Answer
import com.mjh.mathseducationapplication.model.table.AnswerTable

/**
 * A class representing the [AddAnswerActivity] controller.
 *
 * This class provides the functionality of the [AddAnswerActivity] such as setting the view and
 * button functionality.
 */
class AddAnswerActivity : AppCompatActivity() {
    /*---- Fields ----*/
    private lateinit var answerTable: AnswerTable

    /*---- Methods ----*/
    /**
     * Provides functionality in order to add an answer to the Answer Table.
     *
     * @param view The area responsible for drawing and event handling.
     */
    fun addAnswer(view: View) {
        val enteredAnswer: String = findViewById<EditText>(R.id.editTextAnswer).text.toString().trim()

        if(enteredAnswer.isEmpty()) {
            Toast.makeText(this, "All boxes must be filled!", Toast.LENGTH_SHORT).show()
        } else {
            // Create an Answer with the entered answer and add it to the Answer Table.
            val answer: Answer = Answer(-1, enteredAnswer)
            this.answerTable.addAnswer(answer)

            // After the Answer has been added, go back to the admin section.
            val adminSectionIntent: Intent = Intent(this, AdminSectionActivity::class.java).apply {
                putExtra("admin", intent.getSerializableExtra("admin"))
            }
            startActivity(adminSectionIntent)
        }
    }

    /*---- Overridden Methods ----*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_answer)

        this.answerTable = AnswerTable(this, "MathsEducation.db", 1)
    }
}