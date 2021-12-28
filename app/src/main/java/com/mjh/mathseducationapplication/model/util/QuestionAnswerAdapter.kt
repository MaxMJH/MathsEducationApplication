package com.mjh.mathseducationapplication.model.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.mjh.mathseducationapplication.R
import com.mjh.mathseducationapplication.model.Answer
import com.mjh.mathseducationapplication.model.Question

/**
 * A class representing the QuestionAnswer adapter.
 *
 * This class provides the ability to send model data to a list view.
 *
 * @property appContext A context in which an adapter can be applied to.
 * @property questions A list of type [Question] which should contain all answers from the Question table.
 * @property answers A list of type [Answer] which should contain all answers from the Answer table.
 * @constructor Creates an adapter which can be used to send model data to a list view.
 */
class QuestionAnswerAdapter(
    private val appContext: Context,
    private var questions: List<Question>,
    private var answers: List<Answer>): BaseAdapter() {
    /*---- Fields ----*/
    private val inflater: LayoutInflater = this.appContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    /*---- Methods ----*/
    /**
     * Updates the List containing [Question]s and [Answer]s with a new specified list. Usually used in conjunction with
     * [BaseAdapter.notifyDataSetChanged].
     *
     * @param questions List of type [Question] that contains all questions from the Question table.
     * @param answers List of type [Answer] that contains all answers from the Answer table.
     */
    fun updateList(questions: List<Question>, answers: List<Answer>) {
        this.questions = questions
        this.answers = answers
    }

    /**
     * Returns the size of list [questions].
     *
     * @return An integer value which represents the size of the list [questions].
     */
    override fun getCount(): Int {
        return this.questions.size
    }

    /**
     * Returns an [Any] at a specified index.
     *
     * @return An [Any] from the list [questions].
     */
    override fun getItem(position: Int): Any {
        return this.questions[position]
    }

    /**
     * Returns the question ID of a specified index.
     *
     * @return An integer value which represents the question ID of a specified index of the list [questions].
     */
    override fun getItemId(position: Int): Long {
        return this.questions[position].questionID.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View? = convertView
        view = this.inflater.inflate(R.layout.activity_question_and_answer_list_view, parent, false)

        // Ensure that the both of the two text views in the list view are set to question questions and answer answers.
        view.findViewById<TextView>(R.id.textViewListViewQuestion).text = this.questions[position].question
        view.findViewById<TextView>(R.id.textViewListViewAnswer).text = this.answers[position].answer

        return view
    }
}