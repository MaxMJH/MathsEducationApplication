package com.mjh.mathseducationapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.mjh.mathseducationapplication.model.Admin
import com.mjh.mathseducationapplication.model.table.AdminTable
import com.mjh.mathseducationapplication.model.table.UserTable
import com.mjh.mathseducationapplication.model.util.Validate

class AdminLoginActivity : AppCompatActivity() {
    /*---- Fields ----*/
    private lateinit var userTable: UserTable
    private lateinit var adminTable: AdminTable

    /*---- Methods ----*/
    fun login(view: View) {
        val adminUsername: String = findViewById<EditText>(R.id.editTextAdminUsername).text.toString()
        val adminPassword: String = findViewById<EditText>(R.id.editTextAdminPassword).text.toString()

        if(Validate.validateName(adminUsername) && Validate.validatePassword(adminPassword)) {
            val userID: Int = this.userTable.userExists(adminUsername)

            // If the admin does not exist.
            if(userID == -1) {
                Toast.makeText(this, "This user is not an admin!", Toast.LENGTH_SHORT).show()
            } else {
                val adminID: Int = this.adminTable.adminExists(adminUsername)

                if(this.adminTable.checkPassword(adminUsername, adminPassword)) {
                    val admin: Admin = Admin(adminID, adminUsername, adminPassword, userID)

                    val intent: Intent = Intent(this, AdminSectionActivity::class.java).apply {
                        putExtra("admin", admin)
                    }
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Invalid Username or Password!", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "Invalid Username or Password!", Toast.LENGTH_SHORT).show()
        }
    }

    /*---- Overridden Methods ----*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_login)
        this.userTable = UserTable(this, "MathsEducation.db", 1)
        this.adminTable = AdminTable(this, "MathsEducation.db", 1)
    }
}