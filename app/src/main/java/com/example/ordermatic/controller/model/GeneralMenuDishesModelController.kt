package com.example.ordermatic.controller.model

import com.example.ordermatic.model.Dish
import com.example.ordermatic.model.DishesType
import com.example.ordermatic.model.authentication.AuthenticationService
import com.example.ordermatic.model.database.DatabaseService

interface GeneralMenuDishesViewControllerInterface {
    fun setDishes(dishes: List<Dish>)
}

class GeneralMenuDishesModelController(private val viewController: GeneralMenuDishesViewControllerInterface) {
    fun getDishes(dishesType: DishesType) {
        AuthenticationService.getCurrentCompanyAccount()?.let { companyAccount ->
            DatabaseService.addDishesSnapshotListener(companyAccount, dishesType) { dishes ->
                viewController.setDishes(dishes)
            }
        }
    }
}