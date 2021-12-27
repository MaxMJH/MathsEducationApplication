package com.mjh.mathseducationapplication.model.table

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.mjh.mathseducationapplication.model.Question
import com.mjh.mathseducationapplication.model.util.DatabaseHelper

class QuestionTable(context: Context, dbName: String, version: Int): DatabaseHelper(context, dbName, version) {
    /*---- Fields ----*/
    private val tableName: String = "Question"
    private val columnQuestionID: String = "questionID"
    private val columnQuestion: String = "question"
    private val columnAnswerID: String = "answerID"
    private val columnQuestionPicture: String = "questionPicture"

    /*---- Methods ----*/
    fun getQuestions(): ArrayList<Question> {
        val database: SQLiteDatabase = this.readableDatabase
        val sqlStatement: String = "SELECT ${this.tableName}.${this.columnQuestionID}, " +
                "${this.tableName}.${this.columnQuestion}, ${this.tableName}.${this.columnAnswerID}, " +
                "${this.tableName}.${this.columnQuestionPicture}, Answer.answer FROM ${this.tableName} INNER JOIN Answer ON " +
                "${this.tableName}.${this.columnAnswerID} = Answer.answerID"

        val cursor: Cursor = database.rawQuery(sqlStatement, null)

        val questionsList: ArrayList<Question> = ArrayList()

        if(cursor.moveToFirst()) {
            do {
                questionsList.add(Question(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3)))
            } while(cursor.moveToNext())
        }

        database.close()
        cursor.close()

        return questionsList
    }

    fun addQuestion(question: Question): Boolean {
        val database: SQLiteDatabase = this.writableDatabase
        val contentValues: ContentValues = ContentValues()

        contentValues.put(this.columnQuestion, question.question)
        contentValues.put(this.columnAnswerID, question.answerID)

        val querySuccess: Long = database.insert(this.tableName, null, contentValues)
        database.close()

        return querySuccess != -1L
    }

    fun removeQuestion(question: Question): Boolean {
        val database: SQLiteDatabase = this.writableDatabase

        val querySuccess: Int = database.delete(this.tableName, "${this.columnQuestionID} = ${question.questionID}", null)

        database.close()

        return querySuccess == 1
    }
}