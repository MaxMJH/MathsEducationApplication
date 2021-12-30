package com.mjh.mathseducationapplication.model

import java.io.Serializable

/**
 * A class representing the User model.
 *
 * This class provides the ability to store User data which can be sent to the User table
 * or stored from the User table.
 *
 * @property userID The user ID of the User.
 * @property username The username of the User.
 * @constructor Creates all properties needed which reflects the columns from the User table.
 */
data class User(
    val userID: Int,
    val username: String) : Serializable {}