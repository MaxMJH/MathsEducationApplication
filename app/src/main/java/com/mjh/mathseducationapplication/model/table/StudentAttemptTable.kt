package com.mjh.mathseducationapplication.model.table

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.mjh.mathseducationapplication.model.Student
import com.mjh.mathseducationapplication.model.StudentAttempt
import com.mjh.mathseducationapplication.model.util.DatabaseHelper

class StudentAttemptTable(context: Context, dbName: String, version: Int): DatabaseHelper(context, dbName, version) {
    /*---- Fields ----*/
    override val tableName: String = "StudentAttempt"
    private val columnStudentAttemptID: String = "studentAttemptID"
    private val columnTestID: String = "testID"
    private val columnQuestionID: String = "questionID"
    private val columnStudentAnswerID: String = "studentAnswerID"

    /*---- Methods ----*/
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

    fun getCorrectAttempts(testID: Int): Int {
        val database: SQLiteDatabase = this.readableDatabase
        //val sqlStatement: String = "SELECT \"${this.tableName}\".\"${this.columnStudentAttemptID}\", \"${this.tableName}\".\"${this.columnStudentAnswerID}\", \"Question\".\"answerID\" FROM \"${this.tableName}\" INNER JOIN \"Question\" ON \"${this.tableName}\".\"${this.columnQuestionID}\" = \"Question\".\"questionID\" WHERE \"${this.tableName}\".\"${this.columnTestID}\" = \"${testID}\" AND \"${this.tableName}\".\"${this.columnStudentAnswerID}\" = \"Question\".\"answerID\";"
        val sqlStatement: String = "SELECT \"${this.tableName}\".\"${this.columnStudentAttemptID}\", \"${this.tableName}\".\"${this.columnStudentAnswerID}\", \"QuestionAnswer\".\"answer\" FROM \"${this.tableName}\" INNER JOIN \"Answer\" AS \"StudentAnswer\" ON \"${this.tableName}\".\"${this.columnStudentAnswerID}\" = \"StudentAnswer\".\"answerID\" INNER JOIN \"Answer\" AS \"QuestionAnswer\" ON \"${this.tableName}\".\"${this.columnQuestionID}\" = \"QuestionAnswer\".\"answerID\" INNER JOIN \"Question\" ON \"${this.tableName}\".\"${this.columnQuestionID}\" = \"Question\".\"questionID\" WHERE \"${this.tableName}\".\"${this.columnTestID}\" = $testID AND (\"${this.tableName}\".\"${this.columnStudentAnswerID}\" = \"Question\".\"answerID\" OR \"StudentAnswer\".\"answer\" = \"QuestionAnswer\".\"answer\");"
        val cursor: Cursor = database.rawQuery(sqlStatement, null)

        val correctAttempts: Int = cursor.count

        database.close()
        cursor.close()

        return correctAttempts
    }

    /*---- Overridden Methods ----*/
    override fun onCreate(db: SQLiteDatabase?) {
        val sqlStatement: String = "CREATE TABLE \"${this.tableName}\" (" +
                "\"${this.columnStudentAttemptID}\" INTEGER UNIQUE, " +
                "\"${this.columnTestID}\" INTEGER NOT NULL, " +
                "\"${this.columnQuestionID}\" INTEGER NOT NULL, " +
                "\"${this.columnStudentAnswerID}\" INTEGER NOT NULL," +
                "FOREIGN KEY(\"${this.columnTestID}\") REFERENCES \"Test\"(\"${this.columnTestID}\") ON DELETE CASCADE, " +
                "FOREIGN KEY(\"${this.columnQuestionID}\") REFERENCES \"Question\"(\"${this.columnQuestionID}\") ON DELETE CASCADE, " +
                "FOREIGN KEY(\"${this.columnStudentAnswerID}\") REFERENCES \"Answer\"(\"answerID\") ON DELETE CASCADE, " +
                "PRIMARY KEY(\"${this.columnStudentAttemptID}\" AUTOINCREMENT));"

        db?.execSQL(sqlStatement)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}