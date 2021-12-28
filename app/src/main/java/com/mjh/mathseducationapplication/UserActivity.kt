package com.mjh.mathseducationapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import com.mjh.mathseducationapplication.model.Admin
import com.mjh.mathseducationapplication.model.User
import com.mjh.mathseducationapplication.model.table.AdminTable
import com.mjh.mathseducationapplication.model.table.UserTable
import com.mjh.mathseducationapplication.model.util.UserAdapter

/**
 * A class representing the [UserActivity] controller.
 *
 * This class provides the functionality of the [UserActivity] such as setting the view and
 * button functionality.
 */
class UserActivity : AppCompatActivity() {
    /*---- Fields ----*/
    private lateinit var userTable: UserTable
    private lateinit var adminTable: AdminTable
    private lateinit var adapter: UserAdapter
    private var selectedAnswer: User? = null

    /*---- Methods ----*/
    /**
     * Once the Admin has selected a User from the list view, they will then be able to make said
     * user an Admin. It is essential that the Admin sets the User a password so they can login.
     *
     * @param view The area responsible for drawing and event handling.
     */
    fun makeAdmin(view: View) {
        val enteredPassword: String = findViewById<EditText>(R.id.editTextPassword).text.toString().trim()

        // Check if a User has been selected.
        if(this.selectedAnswer != null) {
            if(enteredPassword.isNotEmpty()) {
                val admin: Admin = Admin(-1, this.selectedAnswer!!.username, enteredPassword, this.selectedAnswer!!.userID)
                this.adminTable.addAdmin(admin)

                this.selectedAnswer = null
            } else {
                Toast.makeText(this, "Make sure you have selected a user and set a password!", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Make sure you have selected a user and set a password!", Toast.LENGTH_SHORT).show()
        }
    }

    /*---- Overridden Methods ----*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        this.userTable = UserTable(this, "MathsEducation.db", 1)
        this.adminTable = AdminTable(this, "MathsEducation.db", 1)

        val users: List<User> = this.userTable.getUsers()

        this.adapter = UserAdapter(applicationContext, users)
        findViewById<ListView>(R.id.listViewUsers).adapter = this.adapter

        // On Click Listener which gets the clicked on User.
        findViewById<ListView>(R.id.listViewUsers).setOnItemClickListener { parent, view, position, id ->
            this.selectedAnswer = users[position]
        }
    }
}