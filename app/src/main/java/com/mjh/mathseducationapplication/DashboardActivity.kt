package com.mjh.mathseducationapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.mjh.mathseducationapplication.model.Student
import com.mjh.mathseducationapplication.model.User
import com.mjh.mathseducationapplication.model.table.AnswerTable
import com.mjh.mathseducationapplication.model.table.QuestionTable
import com.mjh.mathseducationapplication.model.table.StudentTable

/**
 * A class representing the [DashboardActivity] controller.
 *
 * This class provides the functionality of the [DashboardActivity] such as setting the view and
 * button functionality.
 */
class DashboardActivity : AppCompatActivity() {
    /*---- Fields ----*/
    private lateinit var questionTable: QuestionTable
    private lateinit var answerTable: AnswerTable
    private lateinit var studentTable: StudentTable

    /*---- Methods ----*/
    /**
     * Sends the User to the [TestActivity] which starts a test.
     *
     * @param view The area responsible for drawing and event handling.
     */
    fun startTest(view: View) {
        // Check if there are any questions or answers available.
        if(this.questionTable.getQuestions().size == 0 || this.answerTable.getAnswers().size == 0) {
            Toast.makeText(this, "No questions or answers available", Toast.LENGTH_SHORT).show()
        } else {
            val user: User = intent.getSerializableExtra("user") as User
            val student: Student?

            var studentID: Int = this.studentTable.studentExists(user.username)

            // If the user does not exist.
            if(studentID == -1) {
                student = Student(this.studentTable.getNextStudentID(), user.username, user.userID)
                this.studentTable.addStudent(student)
            } else {
                student = Student(studentID, user.username, user.userID)
            }

            // Start new intent that starts the test.
            val testActivityIntent: Intent = Intent(this, TestActivity::class.java).apply {
                putExtra("student", student)
            }

            startActivity(testActivityIntent)
        }
    }

    /**
     * Sends the User to the [AdminLoginActivity] which allows an User to login as an Admin.
     *
     * @param view The area responsible for drawing and event handling.
     */
    fun startAdminSection(view: View) {
        // Start new intent that starts the admin section.
        val intent: Intent = Intent(this, AdminLoginActivity::class.java)
        startActivity(intent)
    }

    /*---- Overridden Methods ----*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val user: User = intent.getSerializableExtra("user") as User
        findViewById<TextView>(R.id.textViewStudentName).text = user.username

        this.questionTable = QuestionTable(this, "MathsEducation.db", 1)
        this.answerTable = AnswerTable(this, "MathsEducation.db", 1)
        this.studentTable = StudentTable(this, "MathsEducation.db", 1)
    }
}