package com.mjh.mathseducationapplication.model.table

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.mjh.mathseducationapplication.model.Answer
import com.mjh.mathseducationapplication.model.util.DatabaseHelper

/**
 * A class representing the Answer table.
 *
 * This class provides the ability to INSERT, SELECT and DELETE from the Answer table.
 *
 * @constructor Creates an entry into the availability of manipulating the Answer table in the database.
 */
class AnswerTable(
    context: Context,
    dbName: String,
    version: Int): DatabaseHelper(context, dbName, version) {
    /*---- Fields ----*/
    private val tableName: String = "Answer"
    private val columnAnswerID: String = "answerID"
    private val columnAnswer: String = "answer"

    /*---- Methods ----*/
    /**
     * Attempts to get the [Answer] via a specified ID from the Answer Table.
     *
     * @param answerID A potential ID of an answer.
     * @return An object of type [Answer] which either contains information regarding the specified ID, or default data of [-1, "null"] if the [answerID] is non-existent in the Answer Table.
     */
    fun getAnswer(answerID: Int): Answer {
        val database: SQLiteDatabase = this.readableDatabase
        val sqlStatement: String = "SELECT \"${this.tableName}\".\"${this.columnAnswer}\" FROM " +
                "\"Question\" INNER JOIN \"${this.tableName}\" ON \"Question\".\"${this.columnAnswerID}\" " +
                "= \"${this.tableName}\".\"${this.columnAnswerID}\" WHERE \"Question\".\"${this.columnAnswerID}\" = $answerID;"

        val cursor: Cursor = database.rawQuery(sqlStatement, null)

        if(cursor.moveToFirst()) {
            val answer: Answer = Answer(answerID, cursor.getString(0))

            database.close()
            cursor.close()

            return answer
        }

        return Answer(-1, "null")
    }

    /**
     * Attempts to return all [Answer]s within the Answer Table.
     *
     * @return An Array List of parameter type [Answer] containing all stored information about each [Answer] within the Answer Table.
     */
    fun getAnswers(): ArrayList<Answer> {
        val database: SQLiteDatabase = this.readableDatabase
        val sqlStatement: String = "SELECT * FROM \"${this.tableName}\";"

        val cursor: Cursor = database.rawQuery(sqlStatement, null)

        val answerList: ArrayList<Answer> = ArrayList()

        if(cursor.moveToFirst()) {
            // In order to add the first result from the cursor as well as others, a do-while loop will be used.
            do {
                answerList.add(Answer(cursor.getInt(0), cursor.getString(1)))
            } while(cursor.moveToNext())
        }

        database.close()
        cursor.close()

        return answerList
    }

    /**
     * Adds an [Answer] to the Answer table. As the Answer table has auto-increment regarding answerID,
     * it is therefore not added in the INSERT statement.
     *
     * @param answer Instantiated value of type [Answer] in which will be added to the Answer Table.
     * @return A boolean value that determines whether or not the specified [Answer] was successfully added to the Answer table or not.
     */
    fun addAnswer(answer: Answer): Boolean {
        val database: SQLiteDatabase = this.writableDatabase
        val contentValues: ContentValues = ContentValues()

        contentValues.put(this.columnAnswer, answer.answer)

        val querySuccess: Long = database.insert(this.tableName, null, contentValues)
        database.close()

        return querySuccess != -1L
    }

    /**
     * Aims to return the last entry added to the Answer Table.
     *
     * @return A integer value indicating the last answer ID of the Answer Table, or 1 if no entries exist within the Answer Table.
     */
    fun getLastAnswerID(): Int {
        val database: SQLiteDatabase = this.readableDatabase
        // Below SQL statement throws an IDE error, however it is perfectly fine...
        // Statement aims to get the last entries row ID, if available.
        val sqlStatement: String = "SELECT * FROM \"${this.tableName}\" WHERE \"${this.tableName}\".\"${this.columnAnswerID}\" = (SELECT MAX(\"${this.tableName}\".\"${this.columnAnswerID}\") FROM \"${this.tableName}\");"

        val cursor: Cursor = database.rawQuery(sqlStatement, null)

        var answerID: Int = 1

        if(cursor.moveToFirst()) {
            answerID = cursor.getInt(0)
        }

        database.close()
        cursor.close()

        return answerID
    }

    /**
     * Attempts to remove a specified [Answer] from the Answer Table.
     *
     * @param answer Instantiated value of type [Answer] in which will be removed from the Answer Table.
     * @return A boolean value that determines whether or not the specified [Answer] was successfully removed from the Answer table or not.
     */
    fun removeAnswer(answer: Answer): Boolean {
        val database: SQLiteDatabase = this.writableDatabase

        val querySuccess: Int = database.delete(this.tableName, "${this.columnAnswerID} = ${answer.answerID}", null)

        database.close()

        return querySuccess == 1
    }
}