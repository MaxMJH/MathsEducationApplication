package com.mjh.mathseducationapplication.model.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.mjh.mathseducationapplication.R
import com.mjh.mathseducationapplication.model.User

/**
 * A class representing the User adapter.
 *
 * This class provides the ability to send model data to a list view.
 *
 * @property appContext A context in which an adapter can be applied to.
 * @property users A list of type [User] which should contain all admins from the User table.
 * @constructor Creates an adapter which can be used to send model data to a list view.
 */
class UserAdapter(
    private val appContext: Context,
    private var users: List<User>): BaseAdapter() {
    /*---- Fields ----*/
    private val inflater: LayoutInflater = this.appContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    /*---- Methods ----*/
    /**
     * Returns the size of list [users].
     *
     * @return An integer value which represents the size of the list [users].
     */
    override fun getCount(): Int {
        return this.users.size
    }

    /**
     * Returns an [Any] at a specified index.
     *
     * @return An [Any] from the list [questions].
     */
    override fun getItem(position: Int): Any {
        return this.users[position]
    }

    /**
     * Returns the user ID of a specified index.
     *
     * @return An integer value which represents the user ID of a specified index of the list [users].
     */
    override fun getItemId(position: Int): Long {
        return this.users[position].userID.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View? = convertView
        view = this.inflater.inflate(R.layout.activity_user_list_view, parent, false)

        // Ensure that the text views in the list view are set to user usernames.
        view.findViewById<TextView>(R.id.textViewPassword).text = this.users[position].username

        return view
    }
}