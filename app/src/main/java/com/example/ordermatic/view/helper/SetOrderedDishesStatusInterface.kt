package com.example.ordermatic.view.helper


import com.example.ordermatic.model.OrderDishesStatus
import com.example.ordermatic.model.OrderedDish

interface SetOrderedDishesStatusInterface {

    fun setOrderedDishesStatus(orderedDish: OrderedDish, dishesStatus: OrderDishesStatus)

}