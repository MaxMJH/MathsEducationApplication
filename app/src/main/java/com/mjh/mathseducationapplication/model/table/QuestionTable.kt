package com.mjh.mathseducationapplication.model.table

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.mjh.mathseducationapplication.model.Question
import com.mjh.mathseducationapplication.model.util.DatabaseHelper

/**
 * A class representing the Question table.
 *
 * This class provides the ability to INSERT, SELECT and DELETE from the Question table.
 *
 * @constructor Creates an entry into the availability of manipulating the Question table in the database.
 */
class QuestionTable(
    context: Context,
    dbName: String,
    version: Int): DatabaseHelper(context, dbName, version) {
    /*---- Fields ----*/
    private val tableName: String = "Question"
    private val columnQuestionID: String = "questionID"
    private val columnQuestion: String = "question"
    private val columnAnswerID: String = "answerID"
    private val columnQuestionPicture: String = "questionPicture"

    /*---- Methods ----*/
    /**
     * Attempts to return all [Question]s within the Question Table.
     *
     * @return An Array List of parameter type [Question] containing all stored information about each [Question] within the Question Table.
     */
    fun getQuestions(): ArrayList<Question> {
        val database: SQLiteDatabase = this.readableDatabase
        // Statement aims to return all data regarding a Question as well as it's corresponding answer (text representation, not ID).
        val sqlStatement: String = "SELECT \"${this.tableName}\".\"${this.columnQuestionID}\", " +
                "\"${this.tableName}\".\"${this.columnQuestion}\", \"${this.tableName}\".\"${this.columnAnswerID}\", " +
                "\"${this.tableName}\".\"${this.columnQuestionPicture}\", \"Answer\".\"answer\" FROM \"${this.tableName}\" INNER JOIN \"Answer\" ON " +
                "\"${this.tableName}\".\"${this.columnAnswerID}\" = \"Answer\".\"answerID\";"

        val cursor: Cursor = database.rawQuery(sqlStatement, null)

        val questionsList: ArrayList<Question> = ArrayList()

        if(cursor.moveToFirst()) {
            do {
                // In order to add the first result from the cursor as well as others, a do-while loop will be used.
                questionsList.add(Question(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3)))
            } while(cursor.moveToNext())
        }

        database.close()
        cursor.close()

        return questionsList
    }

    /**
     * Adds a [Question] to the Question table. As the Question table has auto-increment regarding questionID,
     * it is therefore not added in the INSERT statement.
     *
     * @param question Instantiated value of type [Question] in which will be added to the Question Table.
     * @return A boolean value that determines whether or not the specified [Question] was successfully added to the Question table or not.
     */
    fun addQuestion(question: Question): Boolean {
        val database: SQLiteDatabase = this.writableDatabase
        val contentValues: ContentValues = ContentValues()

        contentValues.put(this.columnQuestion, question.question)
        contentValues.put(this.columnAnswerID, question.answerID)

        val querySuccess: Long = database.insert(this.tableName, null, contentValues)
        database.close()

        return querySuccess != -1L
    }

    /**
     * Attempts to remove a specified [Question] from the Question Table.
     *
     * @param question Instantiated value of type [Question] in which will be removed from the Question Table.
     * @return A boolean value that determines whether or not the specified [Question] was successfully removed from the Question table or not.
     */
    fun removeQuestion(question: Question): Boolean {
        val database: SQLiteDatabase = this.writableDatabase

        val querySuccess: Int = database.delete(this.tableName, "\"${this.tableName}\".\"${this.columnQuestionID}\" = ${question.questionID}", null)

        database.close()

        return querySuccess == 1
    }
}