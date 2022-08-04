package com.example.ordermatic.controller.model

import com.example.ordermatic.model.Dish
import com.example.ordermatic.model.DishesType
import com.example.ordermatic.model.authentication.AuthenticationService
import com.example.ordermatic.model.database.DatabaseService

interface ServiceAddDishesToOrderViewControllerInterface {
    fun setDishesListView(dishes: List<Dish>)
}

class ServiceAddDishesToOrderModelController(private val viewController: ServiceAddDishesToOrderViewControllerInterface?) {

    fun getDishesListView(dishesType: DishesType) {
        viewController?.let { viewControllerObject ->
            AuthenticationService.getCurrentCompanyAccount()?.let { companyAccount ->
                DatabaseService.getDishes(companyAccount, dishesType) { dishes ->
                    viewControllerObject.setDishesListView(dishes)
                }
            }
        }
    }

}