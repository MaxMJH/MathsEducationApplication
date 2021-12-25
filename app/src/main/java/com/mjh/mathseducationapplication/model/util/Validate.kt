package com.mjh.mathseducationapplication.model.util

class Validate {
    companion object {
        fun validateName(name: String): Boolean {
            if(name.length in 3..25) {
                if(!"[\\p{Punct}]".toRegex().containsMatchIn(name)) {
                    return true
                }
            }

            return false
        }

        fun validatePassword(password: String) = password.length in 8..25
    }
}