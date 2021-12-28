package com.mjh.mathseducationapplication.model.util

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * A class representing the DatabaseHelper.
 *
 * This class provides the ability to create all tables needed for the programme.
 *
 * @constructor Extends SQLiteOpenHelper in order to create a link to a specified database.
 */
open class DatabaseHelper(
    context: Context,
    dbName: String,
    version: Int): SQLiteOpenHelper(context, dbName, null, version) {
    /*---- Fields ----*/
    private val userTable: String = "CREATE TABLE \"User\" (" +
            "\"userID\" INTEGER NOT NULL UNIQUE, " +
            "\"username\" TEXT NOT NULL UNIQUE, " +
            "PRIMARY KEY(\"userID\" AUTOINCREMENT));"

    private val adminTable: String = "CREATE TABLE \"Admin\" (" +
            "\"adminID\" INTEGER UNIQUE, " +
            "\"adminUsername\" TEXT NOT NULL, " +
            "\"adminPassword\" TEXT NOT NULL, " +
            "\"userID\" INTEGER NOT NULL UNIQUE, " +
            "PRIMARY KEY(\"adminID\" AUTOINCREMENT));"

    private val studentTable: String = "CREATE TABLE \"Student\" (" +
            "\"studentID\" INTEGER UNIQUE, " +
            "\"studentName\" TEXT NOT NULL, " +
            "\"userID\" INTEGER NOT NULL, " +
            "FOREIGN KEY(\"userID\") REFERENCES \"User\"(\"userID\"), " +
            "PRIMARY KEY(\"studentID\" AUTOINCREMENT));"

    private val answerTable: String = "CREATE TABLE \"Answer\" (" +
            "\"answerID\" INTEGER UNIQUE, " +
            "\"answer\" TEXT NOT NULL, " +
            "PRIMARY KEY(\"answerID\" AUTOINCREMENT));"

    private val questionTable: String = "CREATE TABLE \"Question\" (" +
            "\"questionID\" INTEGER UNIQUE, " +
            "\"question\" TEXT NOT NULL, " +
            "\"answerID\" INTEGER NOT NULL, " +
            "\"questionPicture\" TEXT NOT NULL DEFAULT 'lion', " +
            "FOREIGN KEY(\"answerID\") REFERENCES \"Answer\"(\"answerID\") ON DELETE CASCADE, " +
            "PRIMARY KEY(\"questionID\" AUTOINCREMENT));"

    private val testTable: String = "CREATE TABLE \"Test\" (" +
            "\"testID\" INTEGER UNIQUE, " +
            "\"studentID\" INTEGER NOT NULL, " +
            "\"result\" REAL NOT NULL, " +
            "FOREIGN KEY(\"studentID\") REFERENCES \"Student\" ON DELETE CASCADE, " +
            "PRIMARY KEY(\"testID\" AUTOINCREMENT));"

    private val studentAttempt: String = "CREATE TABLE \"StudentAttempt\" (" +
            "\"studentAttemptID\" INTEGER UNIQUE, " +
            "\"testID\" INTEGER NOT NULL, " +
            "\"questionID\" INTEGER NOT NULL, " +
            "\"studentAnswerID\" INTEGER NOT NULL," +
            "FOREIGN KEY(\"testID\") REFERENCES \"Test\"(\"testID\") ON DELETE CASCADE, " +
            "FOREIGN KEY(\"questionID\") REFERENCES \"Question\"(\"questionID\") ON DELETE CASCADE, " +
            "FOREIGN KEY(\"studentAnswerID\") REFERENCES \"Answer\"(\"answerID\") ON DELETE CASCADE, " +
            "PRIMARY KEY(\"studentAttemptID\" AUTOINCREMENT));"

    /*---- Overridden Methods ----*/
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(this.userTable)
        db?.execSQL(this.studentTable)
        db?.execSQL(this.adminTable)
        db?.execSQL(this.answerTable)
        db?.execSQL(this.questionTable)
        db?.execSQL(this.testTable)
        db?.execSQL(this.studentAttempt)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}