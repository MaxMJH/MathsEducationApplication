package com.mjh.mathseducationapplication.model.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.mjh.mathseducationapplication.R
import com.mjh.mathseducationapplication.model.Admin

class AdminAdapter(private val appContext: Context, private var admins: List<Admin>): BaseAdapter() {
    /*---- Fields ----*/
    private val inflater: LayoutInflater = this.appContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    /*---- Methods ----*/
    fun updateList(admins: List<Admin>) {
        this.admins = admins
    }

    override fun getCount(): Int {
        return this.admins.size
    }

    override fun getItem(position: Int): Any {
        return this.admins[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View? = convertView
        view = this.inflater.inflate(R.layout.activity_answers_list_view, parent, false)

        view.findViewById<TextView>(R.id.textViewListViewAnswer).text = this.admins[position].adminUsername

        return view
    }
}