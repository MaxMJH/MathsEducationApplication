package com.mjh.mathseducationapplication.model

/**
 * A class representing the Question model.
 *
 * This class provides the ability to store [Question] data which can be sent to the Question table
 * or stored from the Question table.
 *
 * @property questionID The question ID of the [Question].
 * @property question The question of the [Question].
 * @property answerID The answer ID of the [Question].
 * @property questionPicture The question picture if the [Question].
 * @constructor Creates all properties needed which reflects the columns from the Question table.
 */
data class Question(
    val questionID: Int,
    val question: String,
    val answerID: Int,
    val questionPicture: String) {}