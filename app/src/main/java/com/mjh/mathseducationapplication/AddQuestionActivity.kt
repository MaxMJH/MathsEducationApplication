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
import com.mjh.mathseducationapplication.model.table.QuestionTable

class AddQuestionActivity : AppCompatActivity() {
    /*---- Fields ----*/
    private lateinit var questionTable: QuestionTable
    private lateinit var answerTable: AnswerTable

    /*---- Methods ----*/
    fun submitQuestionsAndAnswers(view: View) {
        val question: String = findViewById<EditText>(R.id.editTextQuestion).text.toString().trim()
        val answer: String = findViewById<EditText>(R.id.editTextCorrectAnswer).text.toString().trim()
        val incorrectAnswer1: String = findViewById<EditText>(R.id.editTextIncorrectAnswer1).text.toString().trim()
        val incorrectAnswer2: String = findViewById<EditText>(R.id.editTextIncorrectAnswer2).text.toString().trim()
        val incorrectAnswer3: String = findViewById<EditText>(R.id.editTextIncorrectAnswer3).text.toString().trim()
        val incorrectAnswer4: String = findViewById<EditText>(R.id.editTextIncorrectAnswer4).text.toString().trim()

        if(question.isEmpty() || answer.isEmpty() || incorrectAnswer1.isEmpty() || incorrectAnswer2.isEmpty() || incorrectAnswer3.isEmpty() || incorrectAnswer4.isEmpty()) {
            Toast.makeText(this, "All boxes must be filled!", Toast.LENGTH_SHORT).show()
        } else {
            val correctAnswer: Answer = Answer(-1, answer)

            this.answerTable.addAnswer(correctAnswer)
            correctAnswer.answerID = this.answerTable.getLastAnswerID()

            this.questionTable.addQuestion(Question(-1, question, correctAnswer.answerID, ""))

            this.answerTable.addAnswer(Answer(-1, incorrectAnswer1))
            this.answerTable.addAnswer(Answer(-1, incorrectAnswer2))
            this.answerTable.addAnswer(Answer(-1, incorrectAnswer3))
            this.answerTable.addAnswer(Answer(-1, incorrectAnswer4))

            val intent: Intent = Intent(this, AdminSectionActivity::class.java)
            startActivity(intent)
        }
    }

    /*---- Overridden Methods ----*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_question)

        this.questionTable = QuestionTable(this, "MathsEducation.db", 1)
        this.answerTable = AnswerTable(this, "MathsEducation.db", 1)
    }
}