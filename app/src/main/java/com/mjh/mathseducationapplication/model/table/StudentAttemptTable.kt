package com.mjh.mathseducationapplication.model.table

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.mjh.mathseducationapplication.model.StudentAttempt
import com.mjh.mathseducationapplication.model.util.DatabaseHelper

/**
 * A class representing the StudentAttempt table.
 *
 * This class provides the ability to INSERT and SELECT from the StudentAttempt table.
 *
 * @constructor Creates an entry into the availability of manipulating the StudentAttempt table in the database.
 */
class StudentAttemptTable(
    context: Context,
    dbName: String,
    version: Int): DatabaseHelper(context, dbName, version) {
    /*---- Fields ----*/
    private val tableName: String = "StudentAttempt"
    private val columnStudentAttemptID: String = "studentAttemptID"
    private val columnTestID: String = "testID"
    private val columnQuestionID: String = "questionID"
    private val columnStudentAnswerID: String = "studentAnswerID"

    /*---- Methods ----*/
    /**
     * Adds a [StudentAttempt] to the StudentAttempt table. As the StudentAttempt table has auto-increment regarding studentAttemptID,
     * it is therefore not added in the INSERT statement.
     *
     * @param studentAttempt Instantiated value of type [StudentAttempt] in which will be added to the StudentAttempt Table.
     * @return A boolean value that determines whether or not the specified [StudentAttempt] was successfully added to the StudentAttempt table or not.
     */
    fun addAttempt(studentAttempt: StudentAttempt): Boolean {
        val database: SQLiteDatabase = this.writableDatabase
        val contentValues: ContentValues = ContentValues()

        contentValues.put(this.columnTestID, studentAttempt.testID)
        contentValues.put(this.columnQuestionID, studentAttempt.questionID)
        contentValues.put(this.columnStudentAnswerID, studentAttempt.studentAnswerID)

        val querySuccess: Long = database.insert(this.tableName, null, contentValues)
        database.close()

        return querySuccess != -1L
    }

    /**
     * Attempts to return the number of correct attempts for a specified test ID.
     *
     * @param testID Value of which represents a specific test ID.
     * @return An integer value representing the number of correct attempts for a specified test ID.
     */
    fun getCorrectAttempts(testID: Int): Int {
        val database: SQLiteDatabase = this.readableDatabase
        // Statement aims to get all correct attempts. As there are duplicate answers within the answers table,
        // those duplicates are also considered if two of the same answer happens to appear, hence the ugly statement.
        val sqlStatement: String = "SELECT \"${this.tableName}\".\"${this.columnStudentAttemptID}\", " +
                "\"${this.tableName}\".\"${this.columnStudentAnswerID}\", \"QuestionAnswer\".\"answer\" " +
                "FROM \"${this.tableName}\" INNER JOIN \"Answer\" AS \"StudentAnswer\" ON " +
                "\"${this.tableName}\".\"${this.columnStudentAnswerID}\" = \"StudentAnswer\".\"answerID\" " +
                "INNER JOIN \"Answer\" AS \"QuestionAnswer\" ON \"${this.tableName}\".\"${this.columnQuestionID}\" = " +
                "\"QuestionAnswer\".\"answerID\" INNER JOIN \"Question\" ON \"${this.tableName}\".\"${this.columnQuestionID}\" = " +
                "\"Question\".\"questionID\" WHERE \"${this.tableName}\".\"${this.columnTestID}\" = $testID " +
                "AND (\"${this.tableName}\".\"${this.columnStudentAnswerID}\" = \"Question\".\"answerID\" " +
                "OR \"StudentAnswer\".\"answer\" = \"QuestionAnswer\".\"answer\");"
        val cursor: Cursor = database.rawQuery(sqlStatement, null)

        val correctAttempts: Int = cursor.count

        database.close()
        cursor.close()

        return correctAttempts
    }
}