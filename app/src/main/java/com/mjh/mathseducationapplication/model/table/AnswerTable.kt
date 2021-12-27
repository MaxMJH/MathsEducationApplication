package com.mjh.mathseducationapplication.model.table

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.mjh.mathseducationapplication.model.Answer
import com.mjh.mathseducationapplication.model.util.DatabaseHelper

class AnswerTable(context: Context, dbName: String, version: Int): DatabaseHelper(context, dbName, version) {
    /*---- Fields ----*/
    private val tableName: String = "Answer"
    private val columnAnswerID: String = "answerID"
    private val columnAnswer: String = "answer"

    /*---- Methods ----*/
    fun getAnswer(answerID: Int): Answer {
        val database: SQLiteDatabase = this.readableDatabase
        val sqlStatement: String = "SELECT \"Answer\".\"${this.columnAnswer}\" FROM \"Question\" INNER JOIN \"${this.tableName}\" ON \"Question\".\"answerID\" = \"${this.tableName}\".\"${this.columnAnswerID}\" WHERE \"Question\".\"answerID\" = $answerID"

        val cursor: Cursor = database.rawQuery(sqlStatement, null)

        if(cursor.moveToFirst()) {
            val answer: Answer = Answer(answerID, cursor.getString(0))

            database.close()
            cursor.close()

            return answer
        }

        return Answer(-1, "null")
    }

    fun getAnswers(): ArrayList<Answer> {
        val database: SQLiteDatabase = this.readableDatabase
        val sqlStatement: String = "SELECT ${this.tableName}.${this.columnAnswerID}, ${this.tableName}.${this.columnAnswer} FROM ${this.tableName}"

        val cursor: Cursor = database.rawQuery(sqlStatement, null)

        val answerList: ArrayList<Answer> = ArrayList()

        if(cursor.moveToFirst()) {
            do {
                answerList.add(Answer(cursor.getInt(0), cursor.getString(1)))
            } while(cursor.moveToNext())
        }

        database.close()
        cursor.close()

        return answerList
    }

    fun addAnswer(answer: Answer): Boolean {
        val database: SQLiteDatabase = this.writableDatabase
        val contentValues: ContentValues = ContentValues()

        contentValues.put(this.columnAnswer, answer.answer)

        val querySuccess: Long = database.insert(this.tableName, null, contentValues)
        database.close()

        return querySuccess != -1L
    }

    fun getLastAnswerID(): Int {
        val database: SQLiteDatabase = this.readableDatabase
        val sqlStatement: String = "SELECT * FROM ${this.tableName} WHERE ${this.columnAnswerID} = (SELECT MAX(${this.columnAnswerID}) FROM ${this.tableName})"

        val cursor: Cursor = database.rawQuery(sqlStatement, null)

        var answerID: Int = 1

        if(cursor.moveToFirst()) {
            answerID = cursor.getInt(0)
        }

        database.close()
        cursor.close()

        return answerID
    }

    fun removeAnswer(answer: Answer): Boolean {
        val database: SQLiteDatabase = this.writableDatabase

        val querySuccess: Int = database.delete(this.tableName, "${this.columnAnswerID} = ${answer.answerID}", null)

        database.close()

        return querySuccess == 1
    }
}