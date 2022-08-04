package com.example.ordermatic.model.helper

import com.example.ordermatic.model.Dish
import com.example.ordermatic.model.OrderedDish

data class OrderedDishesHelper(
    val orderedDish: OrderedDish,
    val dish: Dish
)

