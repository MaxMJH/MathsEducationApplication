package com.mjh.mathseducationapplication.model.table

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.mjh.mathseducationapplication.model.Student
import com.mjh.mathseducationapplication.model.util.DatabaseHelper

class StudentTable(context: Context, dbName: String, version: Int): DatabaseHelper(context, dbName, version) {
    /*---- Fields ----*/
    private val tableName: String = "Student"
    private val columnStudentID: String = "studentID"
    private val columnStudentName: String = "studentName"
    private val columnUserID: String = "userID"

    /*---- Methods ----*/
    /**
     * Adds a student to the StudentTable of MathsEducation database. If the output of the insert
     * method is -1, then the query has failed, of which will be returned as false. Vice versa,
     * if the query is successful, true will be returned.
     *
     * @param student Student object that will be attempted to be added into Student table.
     * @return true if the student was added or false if the student failed to be added.
     */
    fun addStudent(student: Student): Boolean {
        val database: SQLiteDatabase = this.writableDatabase
        val contentValues: ContentValues = ContentValues()

        contentValues.put(this.columnStudentName, student.studentName)
        contentValues.put(this.columnUserID, student.userID)

        val querySuccess: Long = database.insert(this.tableName, null, contentValues)
        database.close()

        return querySuccess != -1L
    }

    fun getStudent(studentID: Int): Student {
        val database: SQLiteDatabase = this.readableDatabase
        val sqlStatement: String = "SELECT * FROM ${this.tableName} WHERE ${this.columnStudentID} = $studentID"

        val cursor: Cursor = database.rawQuery(sqlStatement, null)
        if(cursor.moveToFirst()) {
            val student: Student = Student(cursor.getInt(0), cursor.getString(1), cursor.getInt(2))

            database.close()
            cursor.close()

            return student
        }

        return Student(-1, "Student does not Exist!", -1)
    }

    fun getNextStudentID(): Int {
        val database: SQLiteDatabase = this.readableDatabase
        val sqlStatement: String = "SELECT * FROM ${this.tableName} WHERE ${this.columnStudentID} = (SELECT MAX(${this.columnStudentID}) FROM ${this.tableName})"

        val cursor: Cursor = database.rawQuery(sqlStatement, null)

        var studentID: Int = 1

        if(cursor.moveToFirst()) {
            studentID = cursor.getInt(0) + 1
        }

        database.close()
        cursor.close()

        return studentID
    }

    fun studentExists(studentName: String): Int {
        val database: SQLiteDatabase = this.readableDatabase
        val sqlStatement: String = "SELECT ${this.columnStudentID} FROM ${this.tableName} WHERE '$studentName' = ${this.columnStudentName}"

        val cursor: Cursor = database.rawQuery(sqlStatement, null)

        var studentID: Int = -1

        if(cursor.moveToFirst()) {
            studentID = cursor.getInt(0)
        }

        database.close()
        cursor.close()

        return studentID
    }
}