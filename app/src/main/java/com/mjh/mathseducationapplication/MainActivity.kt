package com.mjh.mathseducationapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.mjh.mathseducationapplication.model.User
import com.mjh.mathseducationapplication.model.table.UserTable
import com.mjh.mathseducationapplication.model.util.Validate

/**
 * A class representing the [MainActivity] controller.
 *
 * This class provides the functionality of the [MainActivity] such as setting the view and
 * button functionality.
 */
class MainActivity : AppCompatActivity() {
    /*---- Fields ----*/
    private lateinit var userTable: UserTable

    /*---- Methods ----*/
    /**
     * Sends the User to the [DashboardActivity] which allows them to take a test, view their
     * progress, or login to the admin section.
     *
     * @param view The area responsible for drawing and event handling.
     */
    fun start(view: View) {
        val studentName: String = findViewById<EditText>(R.id.editTextStudentName).text.toString()
        val user: User?

        // Check to see if the entered name is valid.
        if(Validate.validateName(studentName)) {
            val userID: Int = this.userTable.userExists(studentName)

            // If the user does not exist.
            if(userID == -1) {
                user = User(this.userTable.getNextUserID(), studentName)
                this.userTable.addUser(user)
            } else {
                user = User(userID, studentName)
            }

            val intent: Intent = Intent(this, DashboardActivity::class.java).apply {
                putExtra("user", user)
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
    }
}