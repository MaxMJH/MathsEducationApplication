package com.mjh.mathseducationapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.Toast
import com.mjh.mathseducationapplication.model.Answer
import com.mjh.mathseducationapplication.model.Question
import com.mjh.mathseducationapplication.model.table.AnswerTable
import com.mjh.mathseducationapplication.model.table.QuestionTable
import com.mjh.mathseducationapplication.model.util.QuestionAnswerAdapter

class ViewQuestionsWithAnswersActivity : AppCompatActivity() {
    /*---- Fields ----*/
    private lateinit var questionTable: QuestionTable
    private lateinit var answerTable: AnswerTable
    private lateinit var adapter: QuestionAnswerAdapter
    private var selectedQuestion: Question? = null

    /*---- Methods ----*/
    fun remove(view: View) {
        if(this.selectedQuestion != null) {
            this.questionTable.removeQuestion(this.selectedQuestion!!)

            this.adapter.updateList(this.questionTable.getQuestions(), this.answerTable.getAnswers())
            this.adapter.notifyDataSetChanged()

            this.selectedQuestion = null
        } else {
            Toast.makeText(this, "You must select a question to delete!", Toast.LENGTH_SHORT).show()
        }
    }

    /*---- Overridden Methods ----*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_questions_with_answers)

        this.questionTable = QuestionTable(this, "MathsEducation.db", 1)
        this.answerTable = AnswerTable(this, "MathsEducation.db", 1)

        val questions: List<Question> = this.questionTable.getQuestions().toList()
        val answers: List<Answer> = questions.map { this.answerTable.getAnswer(it.answerID) }.toList()

        this.adapter = QuestionAnswerAdapter(applicationContext, questions, answers)
        findViewById<ListView>(R.id.listViewQuestionsAndAnswers).adapter = this.adapter
        findViewById<ListView>(R.id.listViewQuestionsAndAnswers).setOnItemClickListener { parent, view, position, id ->
            this.selectedQuestion = questions[position]
        }
    }
}