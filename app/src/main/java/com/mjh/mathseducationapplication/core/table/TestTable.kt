package com.mjh.mathseducationapplication.core.table

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.mjh.mathseducationapplication.model.User
import com.mjh.mathseducationapplication.model.Test
import com.mjh.mathseducationapplication.core.util.DatabaseHelper

/**
 * A class representing the Test table.
 *
 * This class provides the ability to INSERT, SELECT and UPDATE from the Test table.
 *
 * @constructor Creates an entry into the availability of manipulating the Test table in the database.
 */
class TestTable(
    context: Context,
    dbName: String,
    version: Int): DatabaseHelper(context, dbName, version) {
    /*---- Fields ----*/
    private val tableName: String = "Test"
    private val columnTestID: String = "testID"
    private val columnUserID: String = "userID"
    private val columnResult: String = "result"

    /*---- Methods ----*/
    /**
     * Adds a [Test] to the Test table. As the Test table has auto-increment regarding testID,
     * it is therefore not added in the INSERT statement.
     *
     * @param test Instantiated value of type [Test] in which will be added to the Test Table.
     * @return A boolean value that determines whether or not the specified [Test] was successfully added to the Test table or not.
     */
    fun addTest(test: Test): Boolean {
        val database: SQLiteDatabase = this.readableDatabase
        val contentValues: ContentValues = ContentValues()

        contentValues.put(this.columnUserID, test.userID)
        contentValues.put(this.columnResult, -1.0)

        val querySuccess: Long = database.insert(this.tableName, null, contentValues)
        database.close()

        return querySuccess != -1L
    }

    /**
     * Aims to return the last entry added to the Test Table.
     *
     * @return An integer value indicating the last test ID of the Test Table, or 1 if no entries exist within the Test Table.
     */
    fun getLastTestID(): Int {
        val database: SQLiteDatabase = this.readableDatabase
        // Statement aims to get the last entry ID from the Test Table.
        val sqlStatement: String = "SELECT * FROM \"${this.tableName}\" WHERE \"${this.tableName}\".\"${this.columnTestID}\" = (SELECT MAX(\"${this.tableName}\".\"${this.columnTestID}\") FROM \"${this.tableName}\");"

        val cursor: Cursor = database.rawQuery(sqlStatement, null)

        var testID: Int = 1

        if(cursor.moveToFirst()) {
            testID = cursor.getInt(0)
        }

        database.close()
        cursor.close()

        return testID
    }

    /**
     * Aims to update a specified test's result with a new result.
     *
     * @param test Instantiated value of type [Test] in which will contains updated values.
     * @return A boolean value that determines whether or not the specified [Test] was successfully updated in the Test table or not.
     */
    fun updateTest(test: Test): Boolean {
        val database: SQLiteDatabase = this.writableDatabase
        val contentValues: ContentValues = ContentValues()

        contentValues.put(this.columnResult, test.result)

        val querySuccess: Int = database.update(this.tableName, contentValues, "${this.columnTestID} = ${test.testID}", null)

        database.close()
        return querySuccess == 1
    }

    /**
     * Aims to return the specified [User]'s test results.
     *
     * @param userID A specified value representing a [User]'s ID.
     * @return An Array List of parameter type [Test] containing all stored information about each [Test] within the Test Table.
     */
    fun getUserTestResults(userID: Int): ArrayList<Test> {
        val database: SQLiteDatabase = this.readableDatabase
        // Statement that aims to provide a student's test results.
        val sqlStatement: String = "SELECT \"${this.tableName}\".\"${this.columnTestID}\", \"${this.tableName}\".\"${this.columnResult}\" FROM \"${this.tableName}\" WHERE \"${this.tableName}\".\"${this.columnUserID}\" = $userID;"

        val cursor: Cursor = database.rawQuery(sqlStatement, null)

        val testsList: ArrayList<Test> = ArrayList()

        if(cursor.moveToFirst()) {
            do {
                // In order to add the first result from the cursor as well as others, a do-while loop will be used.
                testsList.add(Test(cursor.getInt(0), userID, cursor.getDouble(1)))
            } while(cursor.moveToNext())
        }

        database.close()
        cursor.close()

        return testsList
    }

    /**
     * Aims to remove all tests that have a result of -1. A test would have the result of -1 if a student
     * failed to finish the entire test.
     *
     * @return A boolean value that determines whether or not tests with a result of -1 have been removed or not.
     */
    fun removeIncompleteTests(): Boolean {
        val database: SQLiteDatabase = this.writableDatabase

        val querySuccess: Int = database.delete(this.tableName, "\"${this.tableName}\".\"${this.columnResult}\" = -1", null)

        database.close()

        return querySuccess >= 1
    }
}