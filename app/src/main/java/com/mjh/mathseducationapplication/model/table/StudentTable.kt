package com.mjh.mathseducationapplication.model.table

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.mjh.mathseducationapplication.model.Student
import com.mjh.mathseducationapplication.model.util.DatabaseHelper

class StudentTable(context: Context, dbName: String, version: Int): DatabaseHelper(context, dbName, version) {
    /*---- Fields ----*/
    override val tableName: String = "Student"
    private val columnStudentID: String = "studentID"
    private val columnStudentName: String = "studentName"

    /*---- Methods ----*/
    fun addStudent(student: Student): Boolean {
        val database: SQLiteDatabase = this.writableDatabase
        val contentValues: ContentValues = ContentValues()

        contentValues.put(this.columnStudentName, student.studentName)

        val querySuccess: Long = database.insert(this.tableName, null, contentValues)
        database.close()

        return querySuccess != -1L
    }

    fun removeStudent(student: Student): Boolean {
        val database: SQLiteDatabase = this.writableDatabase
        val result: Int = database.delete(this.tableName, "${this.columnStudentID} = ${student.studentID}", null)

        database.close()
        return result == 1
    }

    fun updateStudent(student: Student): Boolean {
        val database: SQLiteDatabase = this.writableDatabase
        val contentValues: ContentValues = ContentValues()

        contentValues.put(this.columnStudentName, student.studentName)

        val result: Int = database.update(this.tableName, contentValues, "${this.columnStudentID} = ${student.studentID}", null)
        database.close()

        return result == 1
    }

    fun getStudent(studentID: Int): Student {
        val database: SQLiteDatabase = this.readableDatabase
        val sqlStatement: String = "SELECT * FROM ${this.tableName} WHERE ${this.columnStudentID} = $studentID"

        val cursor: Cursor = database.rawQuery(sqlStatement, null)
        if(cursor.moveToFirst()) {
            val student: Student = Student(cursor.getInt(0), cursor.getString(1))

            database.close()
            cursor.close()

            return student
        }

        return Student(-1, "Student does not Exist!")
    }

    fun getStudents(): ArrayList<Student> {
        val database: SQLiteDatabase = this.readableDatabase
        val sqlStatement: String = "SELECT * FROM ${this.tableName}"

        val cursor: Cursor = database.rawQuery(sqlStatement, null)

        val studentList: ArrayList<Student> = ArrayList()

        if(cursor.moveToFirst()) {
            do {
                studentList.add(Student(cursor.getInt(0), cursor.getString(1)))
            } while(cursor.moveToNext())
        }

        database.close()
        cursor.close()

        return studentList
    }

    /*---- Overridden Methods ----*/
    override fun onCreate(db: SQLiteDatabase?) {
        val sqlStatement: String = "CREATE TABLE ${this.tableName} (" +
                "${this.columnStudentID} INTEGER UNIQUE, " +
                "${this.columnStudentName} TEXT NOT NULL, " +
                "PRIMARY KEY(${this.columnStudentID} AUTOINCREMENT));"

        println("DONE!")

        db?.execSQL(sqlStatement)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}