package com.example.ordermatic.model

import java.io.Serializable

data class Order(
    val companyReference: String,
    val userReference: String,
    val tableNumber: String,
    var isActive: Boolean,
    val createdAt: String, // format "yyyy-MM-dd"
    val documentId: String? = null
) : Serializable {

    companion object {
        const val COLLECTION_NAME = "orders"

        fun toMap(order: Order): Map<String, Any> {
            return mapOf(
                "companyReference" to order.companyReference,
                "userReference" to order.userReference,
                "tableNumber" to order.tableNumber,
                "isActive" to order.isActive,
                "createdAt" to order.createdAt
            )
        }

        fun toObject(documentId: String, map: Map<String, Any>): Order? {
            return if (
                map["companyReference"] == null
                || map["userReference"] == null
                || map["tableNumber"] == null
                || map["isActive"] == null
                || map["createdAt"] == null
            )
                null
            else
                Order(
                    map["companyReference"]!! as String,
                    map["userReference"]!! as String,
                    map["tableNumber"]!! as String,
                    map["isActive"]!! as Boolean,
                    map["createdAt"] as String,
                    documentId
                )
        }
    }
}