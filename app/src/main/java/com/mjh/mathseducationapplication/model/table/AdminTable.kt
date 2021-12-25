package com.mjh.mathseducationapplication.model.table

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.mjh.mathseducationapplication.model.Admin
import com.mjh.mathseducationapplication.model.util.DatabaseHelper

class AdminTable(context: Context, dbName: String, version: Int): DatabaseHelper(context, dbName, version) {
    /*---- Fields ----*/
    private val tableName: String = "Admin"
    private val columnAdminID: String = "adminID"
    private val columnAdminUsername: String = "adminUsername"
    private val columnAdminPassword: String = "adminPassword"
    private val columnUserID: String = "userID"

    /*---- Methods ----*/
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

    fun getAdmin(adminID: Int): Admin {
        val database: SQLiteDatabase = this.readableDatabase
        val sqlStatement: String = "SELECT * FROM ${this.tableName} WHERE ${this.columnAdminID} = $adminID"

        val cursor: Cursor = database.rawQuery(sqlStatement, null)
        if(cursor.moveToFirst()) {
            val admin: Admin = Admin(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3))

            database.close()
            cursor.close()

            return admin
        }

        return Admin(-1, "Student does not Exist!", "Student does not Exist!", -1)
    }

    fun getNextAdminID(): Int {
        val database: SQLiteDatabase = this.readableDatabase
        val sqlStatement: String = "SELECT * FROM ${this.tableName} WHERE ${this.columnAdminID} = (SELECT MAX(${this.columnAdminID}) FROM ${this.tableName})"

        val cursor: Cursor = database.rawQuery(sqlStatement, null)

        var adminID: Int = 1

        if(cursor.moveToFirst()) {
            adminID = cursor.getInt(0) + 1
        }

        database.close()
        cursor.close()

        return adminID
    }

    fun adminExists(adminUsername: String): Int {
        val database: SQLiteDatabase = this.readableDatabase
        val sqlStatement: String = "SELECT ${this.columnAdminID} FROM ${this.tableName} WHERE '$adminUsername' = ${this.columnAdminUsername}"

        val cursor: Cursor = database.rawQuery(sqlStatement, null)

        var adminID: Int = -1

        if(cursor.moveToFirst()) {
            adminID = cursor.getInt(0)
        }

        database.close()
        cursor.close()

        return adminID
    }
}