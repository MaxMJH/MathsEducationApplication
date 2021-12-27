package com.mjh.mathseducationapplication.model.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.mjh.mathseducationapplication.R
import com.mjh.mathseducationapplication.model.User

class UserAdapter(private val appContext: Context, private var users: List<User>): BaseAdapter() {
    /*---- Fields ----*/
    private val inflater: LayoutInflater = this.appContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    /*---- Methods ----*/
    fun updateList(users: List<User>) {
        this.users = users
    }

    override fun getCount(): Int {
        return this.users.size
    }

    override fun getItem(position: Int): Any {
        return this.users[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View? = convertView
        view = this.inflater.inflate(R.layout.activity_user_list_view, parent, false)

        view.findViewById<TextView>(R.id.textViewPassword).text = this.users[position].username

        return view
    }
}