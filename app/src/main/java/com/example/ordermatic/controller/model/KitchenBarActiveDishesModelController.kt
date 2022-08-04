package com.example.ordermatic.controller.model

import com.example.ordermatic.model.*
import com.example.ordermatic.model.authentication.AuthenticationService
import com.example.ordermatic.model.database.DatabaseService
import com.example.ordermatic.model.helper.OrderedDishesHelper

interface KitchenBarActiveDishesViewControllerInterface {
    fun setActiveDishes(list: List<OrderedDishesHelper>)
}

class KitchenBarActiveDishesModelController(private val viewController: KitchenBarActiveDishesViewControllerInterface) {

    fun getActiveDishesOfOrder(order: Order, user: User) {
        AuthenticationService.getCurrentCompanyAccount()?.let { companyAccount ->
            DatabaseService.getAllOrderDishesByStatusSnapshotListener(
                companyAccount,
                listOf(order.documentId!!),
                OrderDishesStatus.UNDONE
            ) { orderedDishes ->
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
                    viewController.setActiveDishes(orderAndDishes)
                }
            }
        }
    }

    fun setStatusOfOrderedDishes(
        orderedDishesIds: List<String>,
        orderReference: String
    ) {
        if (orderedDishesIds.isNotEmpty()) {
            AuthenticationService.getCurrentCompanyAccount()?.let { companyAccount ->
                DatabaseService.getAllOrderDishesByStatus(
                    companyAccount,
                    listOf(orderReference),
                    OrderDishesStatus.UNDONE
                ) { orderedDishes ->
                    val matchedOrderedDishes = orderedDishes
                        .filter { orderedDish -> orderedDishesIds.contains(orderedDish.documentId!!) }
                    for (orderedDish in matchedOrderedDishes) {
                        var temp = orderedDish
                        temp.status = OrderDishesStatus.READY
                        DatabaseService.editOrderedDish(temp) { _ -> }
                    }
                }
            }
        }
    }
}
