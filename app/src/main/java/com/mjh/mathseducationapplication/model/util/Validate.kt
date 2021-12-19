package com.mjh.mathseducationapplication.model.util

class Validate {
    companion object {
        fun validateStudentName(studentName: String): Boolean {
            if(studentName.length in 3..25) {
                if(!"[\\p{Punct}]".toRegex().containsMatchIn(studentName)) {
                    return true
                }
            }

            return false
        }
    }
}