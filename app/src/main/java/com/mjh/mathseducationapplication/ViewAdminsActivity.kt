package com.mjh.mathseducationapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.Toast
import com.mjh.mathseducationapplication.R
import com.mjh.mathseducationapplication.model.Admin
import com.mjh.mathseducationapplication.core.table.AdminTable
import com.mjh.mathseducationapplication.core.util.AdminAdapter

/**
 * A class representing the [ViewAdminsActivity] controller.
 *
 * This class provides the functionality of the [ViewAdminsActivity] such as setting the view and
 * button functionality.
 */
class ViewAdminsActivity : AppCompatActivity() {
    /*---- Fields ----*/
    private lateinit var adminTable: AdminTable
    private lateinit var adapter: AdminAdapter
    private var selectedAnswer: Admin? = null

    /*---- Methods ----*/
    /**
     * Once the Admin has selected an Admin from the list view, they will then be able to remove said
     * Admin.
     *
     * @param view The area responsible for drawing and event handling.
     */
    fun removeAdmin(view: View) {
        // Check that the user has selected an admin.
        if(this.selectedAnswer != null || this.selectedAnswer?.adminID == 1) {
            this.adminTable.removeAdmin(this.selectedAnswer!!)

            this.adapter.updateList(this.adminTable.getAdmins())
            this.adapter.notifyDataSetChanged()

            this.selectedAnswer = null
        } else {
            Toast.makeText(this, "You must select an admin to delete!", Toast.LENGTH_SHORT).show()
        }
    }

    /*---- Overridden Methods ----*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_admins)

        this.adminTable = AdminTable(this, "MathsEducation.db", 1)

        val admins: List<Admin> = this.adminTable.getAdmins()

        this.adapter = AdminAdapter(applicationContext, admins)
        findViewById<ListView>(R.id.listViewAdmins).adapter = this.adapter

        // On Click Listener which gets the clicked on Admin.
        findViewById<ListView>(R.id.listViewAdmins).setOnItemClickListener { parent, view, position, id ->
            this.selectedAnswer = admins[position]
        }
    }
}