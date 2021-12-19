package com.mjh.mathseducationapplication.model.table

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.mjh.mathseducationapplication.model.util.DatabaseHelper

class ResultTable(context: Context, dbName: String, version: Int): DatabaseHelper(context, dbName, version) {
    /*---- Fields ----*/
    override val tableName: String = "Result"
    private val columnResultID: String = "resultID"
    private val columnStudentID: String = "studentID"
    private val columnTestResult: String = "testResult"

    /*---- Methods ----*/

    /*---- Overridden Methods ----*/
    override fun onCreate(db: SQLiteDatabase?) {
        val sqlStatement: String = "CREATE TABLE ${this.tableName} (" +
                "${this.columnResultID} INTEGER UNIQUE, " +
                "${this.columnStudentID} INTEGER NOT NULL, " +
                "${this.columnTestResult} REAL NOT NULL, " +
                "FOREIGN KEY(${this.columnStudentID}) REFERENCES \"Student\" ON DELETE CASCADE, " +
                "PRIMARY KEY(${this.columnResultID} AUTOINCREMENT));"

        db?.execSQL(sqlStatement)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}