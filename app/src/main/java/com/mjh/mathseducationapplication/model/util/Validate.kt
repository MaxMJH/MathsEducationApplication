package com.mjh.mathseducationapplication.model.util

/**
 * A class representing various forms of validation.
 *
 * This class provides the ability to validate input from the user.
 */
class Validate {
    companion object {
        /**
         * Validates a specified name by ensuring it's length is within a range of 3 to 25 and also
         * checking if it contains any punctuation.
         *
         * @param name A name that needs to be validated.
         * @return A boolean value that determines whether or not the specified [name] is valid or not.
         */
        fun validateName(name: String): Boolean {
            if(name.length in 3..25) {
                // Determine whether or not the username contains any punctuation by using regex.
                if(!"[\\p{Punct}]".toRegex().containsMatchIn(name)) {
                    return true
                }
            }

            return false
        }

        /**
         * Validates a specified password by ensuring it's length is within a range of 8 to 25.
         *
         * @param password A password that needs to be validated.
         * @return A boolean value that determines whether or not the specified [password] is valid or not.
         */
        fun validatePassword(password: String) = password.length in 8..25
    }
}