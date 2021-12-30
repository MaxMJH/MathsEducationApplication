package com.mjh.mathseducationapplication.model

/**
 * A class representing the StudentAttempt model.
 *
 * This class provides the ability to store [StudentAttempt] data which can be sent to the StudentAttempt table
 * or stored from the StudentAttempt table.
 *
 * @property studentAttemptID The student attempt ID of the [StudentAttempt].
 * @property testID The test ID of the [StudentAttempt].
 * @property questionID The question ID of the [StudentAttempt].
 * @property studentAnswerID The student answer ID if the [StudentAttempt].
 * @constructor Creates all properties needed which reflects the columns from the StudentAttempt table.
 */
data class StudentAttempt(
    val studentAttemptID: Int,
    val testID: Int,
    val questionID: Int,
    val studentAnswerID: Int) {}