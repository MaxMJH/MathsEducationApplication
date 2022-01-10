package com.mjh.mathseducationapplication.core.table

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.mjh.mathseducationapplication.model.Admin
import com.mjh.mathseducationapplication.core.util.DatabaseHelper

/**
 * A class representing the Admin table.
 *
 * This class provides the ability to INSERT, SELECT and DELETE from the Admin table.
 *
 * @constructor Creates an entry into the availability of manipulating the Admin table in the database.
 */
class AdminTable(
    context: Context,
    dbName: String,
    version: Int): DatabaseHelper(context, dbName, version) {
    /*---- Fields ----*/
    private val tableName: String = "Admin"
    private val columnAdminID: String = "adminID"
    private val columnAdminUsername: String = "adminUsername"
    private val columnAdminPassword: String = "adminPassword"
    private val columnUserID: String = "userID"

    /*---- Methods ----*/
    /**
     * Adds an [Admin] to the Admin table. As the Admin table has auto-increment regarding adminID,
     * it is therefore not added in the INSERT statement.
     *
     * @param admin Instantiated value of type [Admin] in which will be added to the Admin Table.
     * @return A long value of -1L if the [Admin] failed to be inserted into the Admin Table, or the insertion row ID if successful.
     */
    fun addAdmin(admin: Admin): Boolean {
        val database: SQLiteDatabase = this.writableDatabase
        val contentValues: ContentValues = ContentValues()

        contentValues.put(this.columnAdminUsername, admin.adminUsername)
        contentValues.put(this.columnAdminPassword, admin.adminPassword)
        contentValues.put(this.columnUserID, admin.userID)

        val querySuccess: Long = database.insert(this.tableName, null, contentValues)
        database.close()

        return querySuccess != -1L
    }

    /**
     * Checks to see if an admin specified by their username is in the Admin Table.
     *
     * @param adminUsername A potential username of an admin.
     * @return An integer value of -1 if the admin's username does not exist within the Admin Table, or the specified admin's admin ID.
     */
    fun adminExists(adminUsername: String): Int {
        val database: SQLiteDatabase = this.readableDatabase
        val sqlStatement: String = "SELECT \"${this.tableName}\".\"${this.columnAdminID}\" FROM \"${this.tableName}\" WHERE \"${this.tableName}\".\"${this.columnAdminUsername}\" = '$adminUsername' COLLATE NOCASE;"

        val cursor: Cursor = database.rawQuery(sqlStatement, null)

        var adminID: Int = -1

        // Cursor should only return one row due to the nature of unique usernames.
        if(cursor.moveToFirst()) {
            adminID = cursor.getInt(0)
        }

        database.close()
        cursor.close()

        return adminID
    }

    /**
     * Checks to see if an admin specified by their user ID is in the Admin Table.
     *
     * @param userID A potential user ID of an admin.
     * @return A boolean value that determines whether or not the specified user ID is in the Admin Table or not.
     */
    fun adminExists(userID: Int): Boolean {
        val database: SQLiteDatabase = this.readableDatabase
        val sqlStatement: String = "SELECT \"${this.tableName}\".\"${this.columnAdminID}\" FROM \"${this.tableName}\" WHERE \"${this.tableName}\".\"${this.columnUserID}\" = '$userID';"

        val cursor: Cursor = database.rawQuery(sqlStatement, null)

        var isAdmin: Boolean = false

        // Cursor should only return one row due to the nature of unique user IDs.
        if(cursor.moveToFirst()) {
            isAdmin = true
        }

        database.close()
        cursor.close()

        return isAdmin
    }

    /**
     * Checks to see if the specified admin password matches with that of the admin's specified username.
     *
     * @param adminUsername A potential username of an admin.
     * @param password A potential password of an admin.
     * @return A boolean value that determines whether or not the specified password matches that of the admin username's record in Admin Table.
     */
    fun checkPassword(adminUsername: String, password: String): Boolean {
        val database: SQLiteDatabase = this.readableDatabase
        val sqlStatement: String = "SELECT \"${this.tableName}\".\"${this.columnAdminPassword}\" FROM \"${this.tableName}\" WHERE \"${this.tableName}\".\"${this.columnAdminUsername}\" = '$adminUsername' COLLATE NOCASE;"

        val cursor: Cursor = database.rawQuery(sqlStatement, null)

        var adminPassword: String = ""

        if(cursor.moveToFirst()) {
            adminPassword = cursor.getString(0)
        }

        database.close()
        cursor.close()

        return password == adminPassword
    }

    /**
     * Attempts to remove a specified [Admin] from the Admin Table.
     *
     * @param admin Instantiated value of type [Admin] in which will be removed from the Admin Table.
     * @return A boolean value that determines whether or not the specified [Admin] was successfully removed from the Admin table or not.
     */
    fun removeAdmin(admin: Admin): Boolean {
        val database: SQLiteDatabase = this.writableDatabase

        val querySuccess: Int = database.delete(this.tableName, "\"${this.tableName}\".\"${this.columnUserID}\" = ${admin.userID}", null)

        database.close()

        return querySuccess == 1
    }

    /**
     * Attempts to return all [Admin]s within the Admin Table.
     *
     * @return An Array List of parameter type [Admin] containing all stored information about each [Admin] within the Admin Table.
     */
    fun getAdmins(): ArrayList<Admin> {
        val database: SQLiteDatabase = this.readableDatabase
        val sqlStatement: String = "SELECT * FROM \"${this.tableName}\""

        val cursor: Cursor = database.rawQuery(sqlStatement, null)

        val adminList: ArrayList<Admin> = ArrayList()

        if(cursor.moveToFirst()) {
            // In order to add the first result from the cursor as well as others, a do-while loop will be used.
            do {
                adminList.add(Admin(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3)))
            } while(cursor.moveToNext())
        }

        database.close()
        cursor.close()

        return adminList
    }
}