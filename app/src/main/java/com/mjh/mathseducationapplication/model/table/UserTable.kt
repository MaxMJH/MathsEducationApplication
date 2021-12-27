package com.mjh.mathseducationapplication.model.table

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.mjh.mathseducationapplication.model.User
import com.mjh.mathseducationapplication.model.util.DatabaseHelper

class UserTable(context: Context, dbName: String, version: Int): DatabaseHelper(context, dbName, version) {
    /*---- Fields ----*/
    private val tableName: String = "User"
    private val columnUserID: String = "userID"
    private val columnUsername: String = "username"

    /*---- Methods ----*/
    fun addUser(user: User): Boolean {
        val database: SQLiteDatabase = this.readableDatabase
        val contentValues: ContentValues = ContentValues()

        contentValues.put(this.columnUserID, user.userID)
        contentValues.put(this.columnUsername, user.username)

        val querySuccess: Long = database.insert(this.tableName, null, contentValues)
        database.close()

        return querySuccess != -1L
    }

    fun userExists(studentName: String): Int {
        val database: SQLiteDatabase = this.readableDatabase
        val sqlStatement: String = "SELECT ${this.columnUserID} FROM ${this.tableName} WHERE '$studentName' = ${this.columnUsername}"

        val cursor: Cursor = database.rawQuery(sqlStatement, null)

        var userID: Int = -1

        if(cursor.moveToFirst()) {
            userID = cursor.getInt(0)
        }

        database.close()
        cursor.close()

        return userID
    }

    fun getNextUserID(): Int {
        val database: SQLiteDatabase = this.readableDatabase
        val sqlStatement: String = "SELECT * FROM ${this.tableName} WHERE ${this.columnUserID} = (SELECT MAX(${this.columnUserID}) FROM ${this.tableName})"

        val cursor: Cursor = database.rawQuery(sqlStatement, null)

        var userID: Int = 1

        if(cursor.moveToFirst()) {
            userID = cursor.getInt(0) + 1
        }

        database.close()
        cursor.close()

        return userID
    }

    fun getUsers(): ArrayList<User> {
        val database: SQLiteDatabase = this.readableDatabase
        val sqlStatement: String = "SELECT * FROM \"${this.tableName}\""

        val cursor: Cursor = database.rawQuery(sqlStatement, null)

        val usersList: ArrayList<User> = ArrayList()

        if(cursor.moveToFirst()) {
            do {
                usersList.add(User(cursor.getInt(0), cursor.getString(1)))
            } while(cursor.moveToNext())
        }

        database.close()
        cursor.close()

        return usersList
    }
}