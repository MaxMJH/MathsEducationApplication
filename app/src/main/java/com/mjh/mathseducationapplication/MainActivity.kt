package com.mjh.mathseducationapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.mjh.mathseducationapplication.model.Student
import com.mjh.mathseducationapplication.model.User
import com.mjh.mathseducationapplication.model.table.StudentTable
import com.mjh.mathseducationapplication.model.table.UserTable
import com.mjh.mathseducationapplication.model.util.Validate

class MainActivity : AppCompatActivity() {
    /*---- Fields ----*/
    private lateinit var userTable: UserTable
    private lateinit var studentTable: StudentTable

    /*---- Methods ----*/
    fun start(view: View) {
        val studentName: String = findViewById<EditText>(R.id.editTextStudentName).text.toString()
        val student: Student?

        if(Validate.validateName(studentName)) {
            val userID: Int = this.userTable.userExists(studentName)

            // If the student does not exist
            if(userID == -1) {
                val user: User = User(this.userTable.getNextUserID(), studentName)
                this.userTable.addUser(user)

                student = Student(this.studentTable.getNextStudentID(), studentName, user.userID)
                this.studentTable.addStudent(student)
            } else {
                val studentID = this.studentTable.studentExists(studentName)
                student = Student(studentID, studentName, userID)
            }

            val intent: Intent = Intent(this, DashboardActivity::class.java).apply {
                putExtra("student", student)
            }
            startActivity(intent)
        } else {
            Toast.makeText(this, "Invalid Name! Make sure not to use punctuation!", Toast.LENGTH_SHORT).show()
        }
    }

    /*---- Overridden Methods ----*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.userTable = UserTable(this, "MathsEducation.db", 1)
        this.studentTable = StudentTable(this, "MathsEducation.db", 1)
    }
}