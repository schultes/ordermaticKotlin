package com.example.ordermatic.controller.model

import com.example.ordermatic.model.DishesType
import com.example.ordermatic.model.Order
import com.example.ordermatic.model.User
import com.example.ordermatic.model.UserRole
import com.example.ordermatic.model.authentication.AuthenticationService
import com.example.ordermatic.model.database.DatabaseService
import com.example.ordermatic.model.helper.OrderedDishesHelper

interface KitchenBarArchivedDishesViewControllerInterface {
    fun setArchivedDishesOfOrder(list: List<OrderedDishesHelper>)
}

class KitchenBarArchivedDishesModelController(private val viewController: KitchenBarArchivedDishesViewControllerInterface) {

    fun getArchivedDishesOfOrder(order: Order, user: User) {
        AuthenticationService.getCurrentCompanyAccount()?.let { companyAccount ->
            DatabaseService.getOrderedDishes(order) { orderedDishes ->
                var dishesTypes = listOf<DishesType>()
                if (user.role == UserRole.BAR) {
                    dishesTypes += listOf(DishesType.DRINKS)
                } else {
                    dishesTypes += listOf(DishesType.STARTERS, DishesType.MAIN_DISHES, DishesType.DESSERTS)
                }
                var orderAndDishes = mutableListOf<OrderedDishesHelper>()
                DatabaseService.getDishesByTypes(companyAccount, dishesTypes) { dishes ->
                    for (orderedDish in orderedDishes) {
                        val dish = dishes.find { d -> d.documentId == orderedDish.dishReference }
                        dish?.let { dishObject ->
                            orderAndDishes.add(OrderedDishesHelper(orderedDish, dishObject))
                        }
                    }
                    viewController.setArchivedDishesOfOrder(orderAndDishes)
                }
            }
        }
    }

}