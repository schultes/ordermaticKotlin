package com.example.ordermatic.controller.model

import com.example.ordermatic.model.*
import com.example.ordermatic.model.authentication.AuthenticationService
import com.example.ordermatic.model.database.DatabaseService

interface KitchenBarActiveOrdersViewControllerInterface {
    fun setActiveOrders(matchedOrders: List<Order>)
}

class KitchenBarActiveOrdersModelController(private val viewController: KitchenBarActiveOrdersViewControllerInterface) {
    fun getActiveOrders(user: User) {
        AuthenticationService.getCurrentCompanyAccount()?.let { companyAccount ->
            DatabaseService.addOrderSnapshotListener(companyAccount, true) { activeOrders ->
                val orderIds = mutableListOf<String>()
                for (orderedDish in activeOrders) {
                    orderIds.add(orderedDish.documentId!!)
                }
                DatabaseService.getAllOrderDishesByStatusSnapshotListener(
                    companyAccount,
                    orderIds,
                    OrderDishesStatus.UNDONE
                ) { orderedDishes ->
                    val dishesTypes = mutableListOf<DishesType>()
                    if (user.role == UserRole.BAR) {
                        dishesTypes.add(DishesType.DRINKS)
                    } else {
                        dishesTypes += listOf(DishesType.STARTERS, DishesType.MAIN_DISHES, DishesType.DESSERTS)
                    }
                    // Get dishes with given dishesType to check if the orderedDishes are relevant for user
                    DatabaseService.getDishesIdsByTypes(companyAccount, dishesTypes) { ids ->
                        val matchedOrderedDishes = orderedDishes
                            .filter { orderedDish -> ids.contains(orderedDish.dishReference) }
                            .map { orderedDish -> orderedDish.orderReference }
                        val matchedOrders = activeOrders.filter { order ->
                            matchedOrderedDishes.contains(order.documentId!!)
                        }
                        viewController.setActiveOrders(matchedOrders)
                    }
                }
            }
        }
    }

    fun setStatusOfAllOrderedDishes(orderIds: List<String>, status: OrderDishesStatus, user: User) {
        if (orderIds.size > 0) {
            AuthenticationService.getCurrentCompanyAccount()?.let { companyAccount ->
                // All undone orderedDishes
                DatabaseService.getAllOrderDishesByStatus(
                    companyAccount,
                    orderIds,
                    OrderDishesStatus.UNDONE
                ) { orderedDishes ->
                    val dishesTypes = mutableListOf<DishesType>()
                    if (user.role == UserRole.BAR) {
                        dishesTypes.add(DishesType.DRINKS)
                    } else {
                        dishesTypes += listOf(DishesType.STARTERS, DishesType.MAIN_DISHES, DishesType.DESSERTS)
                    }
                    // Get dishes with given dishesType to check if the orderedDishes are relevant for user
                    DatabaseService.getDishesIdsByTypes(companyAccount, dishesTypes) { ids ->
                        val matchedOrderedDishes = orderedDishes
                            .filter { orderedDish -> ids.contains(orderedDish.dishReference) }
                        for (orderedDish in matchedOrderedDishes) {
                            var temp = orderedDish
                            temp.status = status
                            DatabaseService.editOrderedDish(temp) { _ -> }
                        }
                    }
                }
            }
        }
    }

}