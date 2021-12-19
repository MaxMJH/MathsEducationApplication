package com.mjh.mathseducationapplication.model.table

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.mjh.mathseducationapplication.model.util.DatabaseHelper

class QuestionTable(context: Context, dbName: String, version: Int): DatabaseHelper(context, dbName, version) {
    /*---- Fields ----*/
    override val tableName: String = "Question"
    private val columnQuestionID: String = "questionID"
    private val columnQuestionTitle: String = "questionTitle"
    private val columnAnswerID: String = "answerID"

    /*---- Methods ----*/

    /*---- Overridden Methods ----*/
    override fun onCreate(db: SQLiteDatabase?) {
        val sqlStatement: String = "CREATE TABLE ${this.tableName} (" +
                "${this.columnQuestionID} INTEGER UNIQUE, " +
                "${this.columnQuestionTitle} TEXT NOT NULL, " +
                "${this.columnAnswerID} INTEGER NOT NULL, " +
                "FOREIGN KEY(${this.columnAnswerID}) REFERENCES \"Answer\"(\"answerID\") ON DELETE CASCADE, " +
                "PRIMARY KEY(${this.columnQuestionID} AUTOINCREMENT));"

        db?.execSQL(sqlStatement)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}