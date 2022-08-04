package com.example.ordermatic.controller.model

import com.example.ordermatic.model.Order
import com.example.ordermatic.model.OrderDishesStatus
import com.example.ordermatic.model.OrderedDish
import com.example.ordermatic.model.authentication.AuthenticationService
import com.example.ordermatic.model.database.DatabaseService

interface ServiceOrderedDishesOverviewViewControllerInterface {
    fun onOrderedDishesSave(success: Boolean)
    fun setReadyOrderChange(orderedDishes: List<OrderedDish>)
}

class ServiceOrderedDishesOverviewModelController(private val viewController: ServiceOrderedDishesOverviewViewControllerInterface) {

    fun getReadyOrderedDishesOfOrderListener(order: Order) {
        AuthenticationService.getCurrentCompanyAccount()?.let { companyAccount ->
            DatabaseService.getOrderDishesByStatusSnapshotListener(
                companyAccount,
                order,
                OrderDishesStatus.READY
            ) { readyOrderedDishes ->
                viewController.setReadyOrderChange(readyOrderedDishes)
            }
        }
    }

    fun saveOrderedDishes(orderedDishes: List<OrderedDish>) {
        for (dish in orderedDishes) {
            DatabaseService.addOrderedDish(dish) { orderedDish ->
                if (orderedDish != null) {
                    if (orderedDishes.last() == dish)
                        viewController.onOrderedDishesSave(true)
                }
                if (orderedDish == null) {
                    viewController.onOrderedDishesSave(false)
                }
            }
        }
    }
}