package com.mjh.mathseducationapplication.model

import android.content.Intent
import java.io.Serializable

/**
 * A class representing the Admin model.
 *
 * This class provides the ability to store [Admin] data which can be sent to the Admin table
 * or stored from the Admin table.
 *
 * @property adminID The admin ID of the [Admin].
 * @property adminUsername The username of the [Admin].
 * @property adminPassword The password of the [Admin].
 * @property userID The user ID if the [Admin].
 * @constructor Creates all properties needed which reflects the columns from the Admin table.
 * Also implements [Serializable] so it can be stored in an [Intent]'s extra.
 */
data class Admin(
    val adminID: Int,
    val adminUsername: String,
    val adminPassword: String,
    val userID: Int): Serializable {}