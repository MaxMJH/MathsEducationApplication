package com.mjh.mathseducationapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.mjh.mathseducationapplication.model.Student

class TestResultActivity : AppCompatActivity() {
    /*---- Fields ----*/
    private lateinit var student: Student

    /*---- Methods ----*/
    fun backToDashboard(view: View) {
        val intent: Intent = Intent(this, DashboardActivity::class.java).apply {
            putExtra("student", student)
        }

        startActivity(intent)
    }

    /*---- Overridden Methods ----*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_result)

        this.student = intent.getSerializableExtra("student") as Student

        findViewById<TextView>(R.id.textViewStudentName).text = student.studentName
        findViewById<TextView>(R.id.textViewStudentScore).text = "${"%.2f".format(intent.getDoubleExtra("score", 0.0)).toString()}%"
    }
}