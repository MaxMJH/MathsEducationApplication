package com.mjh.mathseducationapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.mjh.mathseducationapplication.model.Answer
import com.mjh.mathseducationapplication.model.Question
import com.mjh.mathseducationapplication.model.table.AnswerTable

class AddAnswerActivity : AppCompatActivity() {
    /*---- Fields ----*/
    private lateinit var answerTable: AnswerTable

    /*---- Methods ----*/
    fun addAnswer(view: View) {
        val enteredAnswer: String = findViewById<EditText>(R.id.editTextAnswer).text.toString().trim()

        if(enteredAnswer.isEmpty()) {
            Toast.makeText(this, "All boxes must be filled!", Toast.LENGTH_SHORT).show()
        } else {
            val answer: Answer = Answer(-1, enteredAnswer)

            this.answerTable.addAnswer(answer)

            val intent: Intent = Intent(this, AdminSectionActivity::class.java)
            startActivity(intent)
        }
    }

    /*---- Overridden Methods ----*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_answer)

        this.answerTable = AnswerTable(this, "MathsEducation.db", 1)
    }
}