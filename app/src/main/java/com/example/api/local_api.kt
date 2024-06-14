package com.example.api

import java.util.UUID

class local_api {
    fun validateUser(user: String, pass: String): String? {
        return if(user == "admin" && pass == "admin")
            UUID.randomUUID().toString()
        else
            null
    }
}