package com.mjh.mathseducationapplication.model.table

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.mjh.mathseducationapplication.model.User
import com.mjh.mathseducationapplication.model.util.DatabaseHelper

/**
 * A class representing the User table.
 *
 * This class provides the ability to INSERT and SELECT from the User table.
 *
 * @constructor Creates an entry into the availability of manipulating the User table in the database.
 */
class UserTable(
    context: Context,
    dbName: String,
    version: Int): DatabaseHelper(context, dbName, version) {
    /*---- Fields ----*/
    private val tableName: String = "User"
    private val columnUserID: String = "userID"
    private val columnUsername: String = "username"

    /*---- Methods ----*/
    /**
     * Adds an [User] to the User table. As the User table has auto-increment regarding userID,
     * it is therefore not added in the INSERT statement.
     *
     * @param user Instantiated value of type [User] in which will be added to the User Table.
     * @return A boolean value that determines whether or not the specified [User] was successfully added to the User table or not.
     */
    fun addUser(user: User): Boolean {
        val database: SQLiteDatabase = this.readableDatabase
        val contentValues: ContentValues = ContentValues()

        contentValues.put(this.columnUserID, user.userID)
        contentValues.put(this.columnUsername, user.username)

        val querySuccess: Long = database.insert(this.tableName, null, contentValues)
        database.close()

        return querySuccess != -1L
    }

    /**
     * Checks to see if a student specified by their name is in the User Table.
     *
     * @param studentName A potential name of an student.
     * @return An integer value of -1 if the student's name does not exist within the User Table, or the specified user's user ID.
     */
    fun userExists(studentName: String): Int {
        val database: SQLiteDatabase = this.readableDatabase
        val sqlStatement: String = "SELECT \"${this.tableName}\".\"${this.columnUserID}\" FROM \"${this.tableName}\" WHERE '$studentName' = \"${this.tableName}\".\"${this.columnUsername}\";"

        val cursor: Cursor = database.rawQuery(sqlStatement, null)

        var userID: Int = -1

        if(cursor.moveToFirst()) {
            userID = cursor.getInt(0)
        }

        database.close()
        cursor.close()

        return userID
    }

    /**
     * Aims to return the next entry ID from the User Table.
     *
     * @return A integer value indicating the next user ID of the User Table, or 1 if no entries exist within the User Table.
     */
    fun getNextUserID(): Int {
        val database: SQLiteDatabase = this.readableDatabase
        // Statement aims to return the last entry ID from the User Table.
        val sqlStatement: String = "SELECT * FROM ${this.tableName} WHERE ${this.columnUserID} = (SELECT MAX(${this.columnUserID}) FROM ${this.tableName})"

        val cursor: Cursor = database.rawQuery(sqlStatement, null)

        var userID: Int = 1

        if(cursor.moveToFirst()) {
            // Add one to the last entry ID in order to get the next ID.
            userID = cursor.getInt(0) + 1
        }

        database.close()
        cursor.close()

        return userID
    }

    /**
     * Attempts to return all [User]s within the User Table.
     *
     * @return An Array List of parameter type [User] containing all stored information about each [User] within the User Table.
     */
    fun getUsers(): ArrayList<User> {
        val database: SQLiteDatabase = this.readableDatabase
        val sqlStatement: String = "SELECT * FROM \"${this.tableName}\";"

        val cursor: Cursor = database.rawQuery(sqlStatement, null)

        val usersList: ArrayList<User> = ArrayList()

        if(cursor.moveToFirst()) {
            do {
                // In order to add the first result from the cursor as well as others, a do-while loop will be used.
                usersList.add(User(cursor.getInt(0), cursor.getString(1)))
            } while(cursor.moveToNext())
        }

        database.close()
        cursor.close()

        return usersList
    }
}