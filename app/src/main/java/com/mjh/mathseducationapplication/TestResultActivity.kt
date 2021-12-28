package com.mjh.mathseducationapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.mjh.mathseducationapplication.model.Student

/**
 * A class representing the [TestResultActivity] controller.
 *
 * This class provides the functionality of the [TestResultActivity] such as setting the view and
 * button functionality.
 */
class TestResultActivity : AppCompatActivity() {
    /*---- Fields ----*/
    private lateinit var student: Student

    /*---- Methods ----*/
    /**
     * Sends the Student to the [DashboardActivity]
     *
     * @param view The area responsible for drawing and event handling.
     */
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