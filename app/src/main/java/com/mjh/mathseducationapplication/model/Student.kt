package com.mjh.mathseducationapplication.model

import android.content.Intent
import java.io.Serializable

/**
 * A class representing the Student model.
 *
 * This class provides the ability to store [Student] data which can be sent to the Student table
 * or stored from the Student table.
 *
 * @property studentID The student ID of the [Student].
 * @property studentName The student name of the [Student].
 * @property userID The user ID of the [Student].
 * @constructor Creates all properties needed which reflects the columns from the Student table.
 * Also implements [Serializable] so it can be stored in an [Intent]'s extra.
 */
data class Student(
    val studentID: Int,
    val studentName: String,
    val userID: Int): Serializable {}