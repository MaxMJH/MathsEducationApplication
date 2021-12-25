package com.mjh.mathseducationapplication.model

import java.io.Serializable

class Admin(val adminID: Int, val adminUsername: String, val adminPassword: String, val userID: Int) : Serializable {
}