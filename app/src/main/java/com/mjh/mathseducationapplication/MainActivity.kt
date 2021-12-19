package com.mjh.mathseducationapplication

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

        if(Validate.validateStudentName(studentName)) {
            val student: Student = Student(0, studentName)
            studentTable.addStudent(student)
        } else {
            Toast.makeText(this, "Invalid Name! Make sure not to use punctuation!", Toast.LENGTH_SHORT).show()
        }
    }
}