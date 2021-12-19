package com.mjh.mathseducationapplication.model.table

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.mjh.mathseducationapplication.model.util.DatabaseHelper

class AnswerTable(context: Context, dbName: String, version: Int): DatabaseHelper(context, dbName, version) {
    /*---- Fields ----*/
    override val tableName: String = "Answer"
    private val columnAnswerID: String = "answerID"
    private val columnAnswer: String = "answer"

    /*---- Methods ----*/

    /*---- Overridden Methods ----*/
    override fun onCreate(db: SQLiteDatabase?) {
        val sqlStatement: String = "CREATE TABLE ${this.tableName} (" +
                "${this.columnAnswerID} INTEGER UNIQUE, " +
                "${this.columnAnswer} TEXT NOT NULL, " +
                "PRIMARY KEY(${this.columnAnswerID} AUTOINCREMENT));"

        db?.execSQL(sqlStatement)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}