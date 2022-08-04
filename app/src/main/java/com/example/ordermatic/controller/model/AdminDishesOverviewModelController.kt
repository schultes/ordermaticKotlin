package com.example.ordermatic.controller.model

import com.example.ordermatic.model.Dish
import com.example.ordermatic.model.DishesType
import com.example.ordermatic.model.authentication.AuthenticationService
import com.example.ordermatic.model.database.DatabaseService

interface AdminDishesOverviewViewControllerInterface {
    fun setDishes(dishes: List<Dish>)
}

class AdminDishesOverviewModelController(private val viewController: AdminDishesOverviewViewControllerInterface) {

    fun getDishes(dishesType: DishesType) {
        AuthenticationService.getCurrentCompanyAccount()?.let { companyAccount ->
            DatabaseService.addDishesSnapshotListener(companyAccount, dishesType) { dishes ->
                viewController.setDishes(dishes)
            }
        }
    }
}