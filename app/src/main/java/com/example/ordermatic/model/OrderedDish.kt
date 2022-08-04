package com.example.ordermatic.model

import java.io.Serializable

data class OrderedDish(
    val companyReference: String,
    val userReference: String,
    val orderReference: String,
    val dishReference: String,
    var status: OrderDishesStatus,
    var comment: String,
    val documentId: String? = null
) : Serializable {

    companion object {
        const val COLLECTION_NAME = "orderedDishes"

        fun toMap(orderedDish: OrderedDish): Map<String, Any> {
            return mapOf(
                "companyReference" to orderedDish.companyReference,
                "userReference" to orderedDish.userReference,
                "orderReference" to orderedDish.orderReference,
                "dishReference" to orderedDish.dishReference,
                "status" to orderedDish.status.rawValue,
                "comment" to orderedDish.comment
            )
        }

        fun toObject(documentId: String, map: Map<String, Any>): OrderedDish? {
            return if (
                map["companyReference"] == null
                || map["userReference"] == null
                || map["orderReference"] == null
                || map["dishReference"] == null
                || map["status"] == null
                || map["comment"] == null
            )
                null
            else
                OrderedDish(
                    map["companyReference"]!! as String,
                    map["userReference"]!! as String,
                    map["orderReference"]!! as String,
                    map["dishReference"]!! as String,
                    OrderDishesStatus(map["status"]!! as String)!!,
                    map["comment"]!! as String,
                    documentId
                )
        }
    }
}
