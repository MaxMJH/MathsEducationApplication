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

class QuestionAnswerAdapter(private val appContext: Context, private var questions: List<Question>, private var answers: List<Answer>): BaseAdapter() {
    /*---- Fields ----*/
    private val inflater: LayoutInflater = this.appContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    /*---- Methods ----*/
    fun updateList(questions: List<Question>, answers: List<Answer>) {
        this.questions = questions
        this.answers = answers
    }

    override fun getCount(): Int {
        return this.questions.size
    }

    override fun getItem(position: Int): Any {
        return this.questions[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View? = convertView
        view = this.inflater.inflate(R.layout.activity_question_and_answer_list_view, parent, false)

        view.findViewById<TextView>(R.id.textViewListViewQuestion).text = this.questions[position].question
        view.findViewById<TextView>(R.id.textViewListViewAnswer).text = this.answers[position].answer

        return view
    }
}