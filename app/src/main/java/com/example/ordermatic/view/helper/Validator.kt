package com.example.ordermatic.view.helper

import android.util.Patterns
import java.util.regex.Pattern

class Validator {

    private var errorMessages = mutableListOf<String>()

    fun checkForEmailPattern(email: String): Boolean {
        val result = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        if (!result) errorMessages.add("Invalid Email!")
        return result
    }

    fun checkForPasswordComplexity(password: String): Boolean {
        val result = Pattern.compile(PASSWORD_PATTERN).matcher(password).matches()
        if (!result) errorMessages.add("Invalid Password: You need at least 6 digits, including uppercase, lowercase and a special character!")
        return result
    }

    fun checkForPasswordEquality(passwordOne: String, passwordTwo: String): Boolean {
        val result = passwordOne == passwordTwo
        if (!result) errorMessages.add("Invalid Password: Provided passwords are not equal!")
        return result
    }

    fun isValid(): Boolean {
        return errorMessages.isEmpty()
    }

    fun getErrorMessages(): List<String> {
        return errorMessages.toList()
    }

    companion object {
        private const val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"
    }
}