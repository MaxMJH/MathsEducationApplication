package com.mjh.mathseducationapplication.core.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.mjh.mathseducationapplication.R
import com.mjh.mathseducationapplication.model.Answer

/**
 * A class representing the Answer adapter.
 *
 * This class provides the ability to send model data to a list view.
 *
 * @property appContext A context in which an adapter can be applied to.
 * @property answers A list of type [Answer] which should contain all answers from the Answer table.
 * @constructor Creates an adapter which can be used to send model data to a list view.
 */
class AnswerAdapter(
    private val appContext: Context,
    private var answers: List<Answer>): BaseAdapter() {
    /*---- Fields ----*/
    private val inflater: LayoutInflater = this.appContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    /*---- Methods ----*/
    /**
     * Updates the List containing [Answer]s with a new specified list. Usually used in conjunction with
     * [BaseAdapter.notifyDataSetChanged].
     *
     * @param answers List of type [Answer] that contains all answers from the Answer table.
     */
    fun updateList(answers: List<Answer>) {
        this.answers = answers
    }

    /*---- Overridden Methods ----*/
    /**
     * Returns the size of list [answers].
     *
     * @return An integer value which represents the size of the list [answers].
     */
    override fun getCount(): Int {
        return this.answers.size
    }

    /**
     * Returns an [Any] at a specified index.
     *
     * @return An [Any] from the list [answers].
     */
    override fun getItem(position: Int): Any {
        return this.answers[position]
    }

    /**
     * Returns the answer ID of a specified index.
     *
     * @return An integer value which represents the answer ID of a specified index of the list [answers].
     */
    override fun getItemId(position: Int): Long {
        return this.answers[position].answerID.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View? = convertView
        view = this.inflater.inflate(R.layout.activity_answers_list_view, parent, false)

        // Ensure that the text views in the list view are set to answer answers.
        view.findViewById<TextView>(R.id.textViewListViewAnswer).text = this.answers[position].answer

        return view
    }
}