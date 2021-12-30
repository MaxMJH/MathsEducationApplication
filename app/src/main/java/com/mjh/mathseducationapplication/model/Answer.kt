package com.mjh.mathseducationapplication.model

/**
 * A class representing the Answer model.
 *
 * This class provides the ability to store Answer data which can be sent to the Answer table
 * or stored from the Answer table.
 *
 * @property answerID The answer ID of the Answer.
 * @property answer The answer of the Answer.
 * @constructor Creates all properties needed which reflects the columns from the Answer table.
 */
data class Answer(
    var answerID: Int,
    val answer: String) {}