package com.mjh.mathseducationapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.Toast
import com.mjh.mathseducationapplication.model.Admin
import com.mjh.mathseducationapplication.model.Answer
import com.mjh.mathseducationapplication.model.table.AdminTable
import com.mjh.mathseducationapplication.model.table.AnswerTable
import com.mjh.mathseducationapplication.model.util.AdminAdapter
import com.mjh.mathseducationapplication.model.util.AnswerAdapter

class ViewAdminsActivity : AppCompatActivity() {
    /*---- Fields ----*/
    private lateinit var adminTable: AdminTable
    private lateinit var adapter: AdminAdapter
    private var selectedAnswer: Admin? = null

    /*---- Methods ----*/
    fun removeAdmin(view: View) {
        if(this.selectedAnswer != null) {
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
        findViewById<ListView>(R.id.listViewAdmins).setOnItemClickListener { parent, view, position, id ->
            this.selectedAnswer = admins[position]
        }
    }
}