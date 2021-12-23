package com.mjh.mathseducationapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.mjh.mathseducationapplication.model.Student
import com.mjh.mathseducationapplication.model.table.TestTable

class DashboardActivity : AppCompatActivity() {
    private lateinit var student: Student
    private lateinit var testTable: TestTable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        this.student = intent.getSerializableExtra("student") as Student
        findViewById<TextView>(R.id.textViewStudentName).text = this.student.studentName

        this.testTable = TestTable(this, "MathsEducation.db", 1)
    }

    fun startTest(view: View) {
        // Start new intent that starts the test.
        val intent: Intent = Intent(this, TestActivity::class.java).apply {
            putExtra("studentID", student.studentID)
        }
        startActivity(intent)
    }

    fun startAdminSection(view: View) {
        // Start new intent that starts the admin section.
    }
}