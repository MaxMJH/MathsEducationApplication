package com.mjh.mathseducationapplication.model.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.mjh.mathseducationapplication.R
import com.mjh.mathseducationapplication.model.Admin

/**
 * A class representing the Admin adapter.
 *
 * This class provides the ability to send model data to a list view.
 *
 * @property appContext A context in which an adapter can be applied to.
 * @property admins A list of type [Admin] which should contain all admins from the Admin table.
 * @constructor Creates an adapter which can be used to send model data to a list view.
 */
class AdminAdapter(
    private val appContext: Context,
    private var admins: List<Admin>): BaseAdapter() {
    /*---- Fields ----*/
    private val inflater: LayoutInflater = this.appContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    /*---- Methods ----*/
    /**
     * Updates the List containing [Admin]s with a new specified list. Usually used in conjunction with
     * [BaseAdapter.notifyDataSetChanged].
     *
     * @param admins List of type [Admin] that contains all admins from the Admin table.
     */
    fun updateList(admins: List<Admin>) {
        this.admins = admins
    }

    /**
     * Returns the size of list [admins].
     *
     * @return An integer value which represents the size of the list [admins].
     */
    override fun getCount(): Int {
        return this.admins.size
    }

    /**
     * Returns an [Any] at a specified index.
     *
     * @return An [Any] from the list [admins].
     */
    override fun getItem(position: Int): Any {
        return this.admins[position]
    }

    /**
     * Returns the admin ID of a specified index.
     *
     * @return An integer value which represents the admin ID of a specified index of the list [admins].
     */
    override fun getItemId(position: Int): Long {
        return this.admins[position].adminID.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View? = convertView
        view = this.inflater.inflate(R.layout.activity_answers_list_view, parent, false)

        // Ensure that the text views in the list view are set to admin usernames.
        view.findViewById<TextView>(R.id.textViewListViewAnswer).text = this.admins[position].adminUsername

        return view
    }
}