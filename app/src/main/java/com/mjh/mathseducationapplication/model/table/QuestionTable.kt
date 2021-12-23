package com.mjh.mathseducationapplication.model.table

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.mjh.mathseducationapplication.model.Question
import com.mjh.mathseducationapplication.model.Student
import com.mjh.mathseducationapplication.model.util.DatabaseHelper

class QuestionTable(context: Context, dbName: String, version: Int): DatabaseHelper(context, dbName, version) {
    /*---- Fields ----*/
    override val tableName: String = "Question"
    private val columnQuestionID: String = "questionID"
    private val columnQuestion: String = "question"
    private val columnAnswerID: String = "answerID"

    /*---- Methods ----*/
    fun getQuestions(): ArrayList<Question> {
        val database: SQLiteDatabase = this.readableDatabase
        val sqlStatement: String = "SELECT ${this.tableName}.${this.columnQuestionID}, " +
                "${this.tableName}.${this.columnQuestion}, ${this.tableName}.${this.columnAnswerID}, " +
                "Answer.answer FROM ${this.tableName} INNER JOIN Answer ON " +
                "${this.tableName}.${this.columnAnswerID} = Answer.answerID"

        val cursor: Cursor = database.rawQuery(sqlStatement, null)

        val questionsList: ArrayList<Question> = ArrayList()

        if(cursor.moveToFirst()) {
            do {
                questionsList.add(Question(cursor.getInt(0), cursor.getString(1), cursor.getInt(2)))
            } while(cursor.moveToNext())
        }

        database.close()
        cursor.close()

        return questionsList
    }

    /*---- Overridden Methods ----*/
    override fun onCreate(db: SQLiteDatabase?) {
        val sqlStatement: String = "CREATE TABLE \"${this.tableName}\" (" +
                "\"${this.columnQuestionID}\" INTEGER UNIQUE, " +
                "\"${this.columnQuestion}\" TEXT NOT NULL, " +
                "\"${this.columnAnswerID}\" INTEGER NOT NULL, " +
                "FOREIGN KEY(\"${this.columnAnswerID}\") REFERENCES \"Answer\"(\"answerID\") ON DELETE CASCADE, " +
                "PRIMARY KEY(\"${this.columnQuestionID}\" AUTOINCREMENT));"

        db?.execSQL(sqlStatement)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}