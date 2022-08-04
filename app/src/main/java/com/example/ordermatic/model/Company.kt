package com.example.ordermatic.model

import java.io.Serializable

data class Company(
    val name: String,
    val addressFirstLine: String,
    val addressSecondLine: String,
    val documentId: String? = null
) : Serializable {

    companion object {
        const val COLLECTION_NAME = "companies"

        fun toMap(company: Company): Map<String, Any> {
            return mapOf(
                "name" to company.name,
                "adressFirstLine" to company.addressFirstLine,
                "adressSecondLine" to company.addressSecondLine
            )
        }

        fun toObject(documentId: String, map: Map<String, Any>): Company? {
            return if (
                map["name"] == null
                || map["adressFirstLine"] == null
                || map["adressSecondLine"] == null
            )
                null
            else
                Company(
                    map["name"]!! as String,
                    map["adressFirstLine"]!! as String,
                    map["adressSecondLine"]!! as String,
                    documentId
                )
        }
    }
}
