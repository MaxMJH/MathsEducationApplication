package com.mjh.mathseducationapplication.model.table

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabaseLockedException
import com.mjh.mathseducationapplication.model.Test
import com.mjh.mathseducationapplication.model.util.DatabaseHelper

class TestTable(context: Context, dbName: String, version: Int): DatabaseHelper(context, dbName, version) {
    /*---- Fields ----*/
    override val tableName: String = "Test"
    private val columnTestID: String = "testID"
    private val columnStudentID: String = "studentID"
    private val columnResult: String = "result"

    /*---- Methods ----*/
    fun addTest(test: Test): Boolean {
        val database: SQLiteDatabase = this.readableDatabase
        val contentValues: ContentValues = ContentValues()

        contentValues.put(this.columnStudentID, test.studentID)
        contentValues.put(this.columnResult, -1.0)

        val querySuccess: Long = database.insert(this.tableName, null, contentValues)
        database.close()

        return querySuccess != -1L
    }

    fun getLastTestID(): Int {
        val database: SQLiteDatabase = this.readableDatabase
        val sqlStatement: String = "SELECT * FROM \"${this.tableName}\" WHERE \"${this.tableName}\".\"${this.columnTestID}\" = (SELECT MAX(\"${this.tableName}\".\"${this.columnTestID}\") FROM \"${this.tableName}\");"

        val cursor: Cursor = database.rawQuery(sqlStatement, null)

        if(cursor.moveToFirst()) {
            return cursor.getInt(0)
        }

        return 1
    }

    fun updateTest(test: Test): Boolean {
        val database: SQLiteDatabase = this.writableDatabase
        val contentValues: ContentValues = ContentValues()

        contentValues.put(this.columnResult, test.result)

        val querySuccess: Int = database.update(this.tableName, contentValues, "${this.columnTestID} = ${test.testID}", null)

        database.close()
        return querySuccess == 1
    }

    /*---- Overridden Methods ----*/
    override fun onCreate(db: SQLiteDatabase?) {
        val sqlStatement: String = "CREATE TABLE \"${this.tableName}\" (" +
                "\"${this.columnTestID}\" INTEGER UNIQUE, " +
                "\"${this.columnStudentID}\" INTEGER NOT NULL, " +
                "\"${this.columnResult}\" REAL NOT NULL, " +
                "FOREIGN KEY(\"${this.columnStudentID}\") REFERENCES \"Student\" ON DELETE CASCADE, " +
                "PRIMARY KEY(\"${this.columnTestID}\" AUTOINCREMENT));"

        db?.execSQL(sqlStatement)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}