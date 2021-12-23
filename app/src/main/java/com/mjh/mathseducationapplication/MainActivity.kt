package com.mjh.mathseducationapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.mjh.mathseducationapplication.model.Student
import com.mjh.mathseducationapplication.model.table.StudentTable
import com.mjh.mathseducationapplication.model.util.Validate

class MainActivity : AppCompatActivity() {
    private lateinit var studentTable: StudentTable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.studentTable = StudentTable(this, "MathsEducation.db", 1)
    }

    fun start(view: View) {
        val studentName: String = findViewById<EditText>(R.id.editTextStudentName).text.toString()
        var student: Student?

        if(Validate.validateStudentName(studentName)) {
            val studentID: Int = this.studentTable.studentExists(studentName)

            // If the student does not exist
            if(studentID == -1) {
                student = Student(this.studentTable.getNextStudentID(), studentName)
                studentTable.addStudent(student)
            } else {
                student = Student(studentID, studentName)
            }

            val intent: Intent = Intent(this, DashboardActivity::class.java).apply {
                putExtra("student", student)
            }
            startActivity(intent)
        } else {
            Toast.makeText(this, "Invalid Name! Make sure not to use punctuation!", Toast.LENGTH_SHORT).show()
        }
    }
}