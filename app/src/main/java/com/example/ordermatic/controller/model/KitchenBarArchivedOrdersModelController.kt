package com.example.ordermatic.controller.model

import com.example.ordermatic.model.DishesType
import com.example.ordermatic.model.Order
import com.example.ordermatic.model.User
import com.example.ordermatic.model.UserRole
import com.example.ordermatic.model.authentication.AuthenticationService
import com.example.ordermatic.model.database.DatabaseService

interface KitchenBarArchivedOrdersViewControllerInterface {
    fun setArchivedOrders(archivedOrders: List<Order>)
}

class KitchenBarArchivedOrdersModelController(private val viewController: KitchenBarArchivedOrdersViewControllerInterface) {

    fun getArchivedOrders(user: User) {
        AuthenticationService.getCurrentCompanyAccount()?.let { companyAccount ->
            DatabaseService.addOrderSnapshotListener(companyAccount, false) { activeOrders ->
                val orderIds = mutableListOf<String>()
                for (orderedDish in activeOrders) {
                    orderIds.add(orderedDish.documentId!!)
                }
                DatabaseService.getAllOrderDishesOfMultipleOrdersSnapshotListener(
                    companyAccount,
                    orderIds
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
                        val matchedOrders = activeOrders.filter { order -> matchedOrderedDishes.contains(order.documentId!!) }
                        viewController.setArchivedOrders(matchedOrders)
                    }
                }
            }
        }
    }

}