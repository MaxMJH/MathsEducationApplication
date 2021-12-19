package com.mjh.mathseducationapplication.model.util

import android.content.Context
import android.database.sqlite.SQLiteOpenHelper

abstract class DatabaseHelper(context: Context, dbName: String, version: Int): SQLiteOpenHelper(context, dbName, null, version) {
    /*---- Fields ----*/
    abstract val tableName: String
}