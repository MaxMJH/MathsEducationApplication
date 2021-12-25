package com.mjh.mathseducationapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.mjh.mathseducationapplication.model.Student
import com.mjh.mathseducationapplication.model.table.AnswerTable
import com.mjh.mathseducationapplication.model.table.QuestionTable

class DashboardActivity : AppCompatActivity() {
    /*---- Fields ----*/
    private lateinit var student: Student
    private lateinit var questionTable: QuestionTable
    private lateinit var answerTable: AnswerTable

    /*---- Methods ----*/
    fun startTest(view: View) {
        if(this.questionTable.getQuestions().size == 0 || this.answerTable.getAnswers().size == 0) {
            Toast.makeText(this, "No questions or answers available", Toast.LENGTH_SHORT).show()
        } else {
            // Start new intent that starts the test.
            val intent: Intent = Intent(this, TestActivity::class.java).apply {
                putExtra("student", student)
            }
            startActivity(intent)
        }
    }

    fun startAdminSection(view: View) {
        // Start new intent that starts the admin section.
        val intent: Intent = Intent(this, AdminLoginActivity::class.java)
        startActivity(intent)
    }

    /*---- Overridden Methods ----*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        this.student = intent.getSerializableExtra("student") as Student
        findViewById<TextView>(R.id.textViewStudentName).text = this.student.studentName

        this.questionTable = QuestionTable(this, "MathsEducation.db", 1)
        this.answerTable = AnswerTable(this, "MathsEducation.db", 1)
    }
}