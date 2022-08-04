package com.example.ordermatic.model

import java.io.Serializable

data class Dish(
    val companyReference: String,
    val name: String,
    val description: String,
    val price: String,
    val type: DishesType,
    val documentId: String? = null
) : Serializable {

    companion object {
        const val COLLECTION_NAME = "dishes"

        fun toMap(dish: Dish): Map<String, Any> {
            return mapOf(
                "companyReference" to dish.companyReference,
                "name" to dish.name,
                "description" to dish.description,
                "price" to dish.price,
                "type" to dish.type.rawValue
            )
        }

        fun toObject(documentId: String, map: Map<String, Any>): Dish? {
            return if (
                map["companyReference"] == null
                || map["name"] == null
                || map["description"] == null
                || map["price"] == null
                || map["type"] == null
            )
                null
            else
                Dish(
                    map["companyReference"]!! as String,
                    map["name"]!! as String,
                    map["description"]!! as String,
                    map["price"]!! as String,
                    DishesType(map["type"]!! as String)!!,
                    documentId
                )
        }
    }
}
