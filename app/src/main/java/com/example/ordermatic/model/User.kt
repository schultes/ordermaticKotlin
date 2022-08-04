package com.example.ordermatic.model

import java.io.Serializable

data class User(
    val companyReference: String,
    val name: String,
    val password: String,
    val role: UserRole,
    val documentId: String? = null
) : Serializable {

    companion object {
        const val COLLECTION_NAME = "users"

        fun toMap(user: User): Map<String, Any> {
            return mapOf(
                "companyReference" to user.companyReference,
                "name" to user.name,
                "password" to user.password,
                "role" to user.role.rawValue
            )
        }

        fun toObject(documentId: String, map: Map<String, Any>): User? {
            return if (
                map["companyReference"] == null
                || map["name"] == null
                || map["password"] == null
                || map["role"] == null
            )
                null
            else
                User(
                    map["companyReference"]!! as String,
                    map["name"]!! as String,
                    map["password"]!! as String,
                    UserRole(map["role"]!! as String)!!,
                    documentId
                )
        }
    }
}