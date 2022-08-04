package com.example.ordermatic.controller.model

import com.example.ordermatic.model.Dish
import com.example.ordermatic.model.database.DatabaseService

interface AdminDishEditViewControllerInterface {
    fun onTransactionResult()
}

class AdminDishEditModelController(private val viewController: AdminDishEditViewControllerInterface) {

    fun addDish(dish: Dish) {
        DatabaseService.addDish(dish) { dishObject ->
            if (dishObject != null) {
                viewController.onTransactionResult()
            }
        }
    }

    fun editDish(dish: Dish) {
        DatabaseService.editDish(dish) { error ->
            if (error == null)
                viewController.onTransactionResult()
        }
    }

    fun deleteDish(dish: Dish) {
        DatabaseService.deleteDish(dish) { error ->
            if (error == null)
                viewController.onTransactionResult()
        }
    }
}