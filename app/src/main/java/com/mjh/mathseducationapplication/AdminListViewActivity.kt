package com.mjh.mathseducationapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/**
 * A class representing the [AdminListViewActivity] controller.
 *
 * This class contains the list view that is used in [ViewAdminsActivity]
 */
class AdminListViewActivity : AppCompatActivity() {
    /*---- Overridden Methods ----*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_list_view)
    }
}