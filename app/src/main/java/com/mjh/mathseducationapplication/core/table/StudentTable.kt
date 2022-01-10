package com.mjh.mathseducationapplication.core.table

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.mjh.mathseducationapplication.model.Student
import com.mjh.mathseducationapplication.core.util.DatabaseHelper

/**
 * A class representing the Student table.
 *
 * This class provides the ability to INSERT, SELECT and DELETE from the Student table.
 *
 * @constructor Creates an entry into the availability of manipulating the Student table in the database.
 */
class StudentTable(
    context: Context,
    dbName: String,
    version: Int): DatabaseHelper(context, dbName, version) {
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

    /**
     * Attempts to get the [Student] via a specified ID from the Student Table.
     *
     * @param studentID A potential ID of a student.
     * @return An object of type [Student] which either contains information regarding the specified ID, or default data of [-1, "Student does not Exist!", -1] if the [studentID] is non-existent in the Student Table.
     */
    fun getStudent(studentID: Int): Student {
        val database: SQLiteDatabase = this.readableDatabase
        val sqlStatement: String = "SELECT * FROM \"${this.tableName}\" WHERE \"${this.tableName}\".\"${this.columnStudentID}\" = $studentID;"

        val cursor: Cursor = database.rawQuery(sqlStatement, null)
        if(cursor.moveToFirst()) {
            val student: Student = Student(cursor.getInt(0), cursor.getString(1), cursor.getInt(2))

            database.close()
            cursor.close()

            return student
        }

        return Student(-1, "Student does not Exist!", -1)
    }

    /**
     * Aims to return the next entry ID from the Student Table.
     *
     * @return A integer value indicating the next student ID of the Student Table, or 1 if no entries exist within the Student Table.
     */
    fun getNextStudentID(): Int {
        val database: SQLiteDatabase = this.readableDatabase
        // Statement aims to return the last entry ID from the Student Table.
        val sqlStatement: String = "SELECT * FROM \"${this.tableName}\" WHERE \"${this.tableName}\".\"${this.columnStudentID}\" = (SELECT MAX(\"${this.tableName}\".\"${this.columnStudentID}\") FROM \"${this.tableName}\");"

        val cursor: Cursor = database.rawQuery(sqlStatement, null)

        var studentID: Int = 1

        if(cursor.moveToFirst()) {
            // Add one to the last entry ID in order to get the next ID.
            studentID = cursor.getInt(0) + 1
        }

        database.close()
        cursor.close()

        return studentID
    }

    /**
     * Checks to see if a student specified by their name is in the Student Table.
     *
     * @param studentName A potential name of an student.
     * @return An integer value of -1 if the student's name does not exist within the Student Table, or the specified student's student ID.
     */
    fun studentExists(studentName: String): Int {
        val database: SQLiteDatabase = this.readableDatabase
        val sqlStatement: String = "SELECT \"${this.tableName}\".\"${this.columnStudentID}\" FROM \"${this.tableName}\" WHERE \"${this.tableName}\".\"${this.columnStudentName}\" = '$studentName';"

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