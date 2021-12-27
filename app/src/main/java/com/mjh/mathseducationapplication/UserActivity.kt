package com.mjh.mathseducationapplication

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

class UserActivity : AppCompatActivity() {
    /*---- Fields ----*/
    private lateinit var userTable: UserTable
    private lateinit var adminTable: AdminTable
    private lateinit var adapter: UserAdapter
    private var selectedAnswer: User? = null

    /*---- Methods ----*/
    fun makeAdmin(view: View) {
        val enteredPassword: String = findViewById<EditText>(R.id.editTextPassword).text.toString().trim()

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
        findViewById<ListView>(R.id.listViewUsers).setOnItemClickListener { parent, view, position, id ->
            this.selectedAnswer = users[position]
        }
    }
}