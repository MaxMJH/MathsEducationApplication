package com.mjh.mathseducationapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/**
 * A class representing the [UserListViewActivity] controller.
 *
 * This class contains the list view that is used in [UserActivity]
 */
class UserListViewActivity : AppCompatActivity() {
    /*---- Overridden Methods ----*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list_view)
    }
}