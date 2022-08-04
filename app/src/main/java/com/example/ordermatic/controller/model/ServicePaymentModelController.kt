package com.example.ordermatic.controller.model

import com.example.ordermatic.model.Order
import com.example.ordermatic.model.OrderDishesStatus
import com.example.ordermatic.model.OrderedDish
import com.example.ordermatic.model.authentication.AuthenticationService
import com.example.ordermatic.model.database.DatabaseService
import com.example.ordermatic.model.helper.OrderedDishesHelper

interface ServicePaymentViewControllerInterface {
    fun setPaymentSuccess(success: Boolean)
    fun setOrderedDishesOfOrder(orderedDishesList: List<OrderedDishesHelper>)
}

class ServicePaymentModelController(private val viewController: ServicePaymentViewControllerInterface) {

    fun archiveOrder(order: Order) {
        var temp = order
        temp.isActive = false
        DatabaseService.editOrder(temp) { error ->
            var success = true
            if (error != null) {
                success = false
            }
            viewController.setPaymentSuccess(success)
        }
    }

    fun getOrderedDishesOfOrder(order: Order) {
        AuthenticationService.getCurrentCompanyAccount()?.let { companyAccount ->
            DatabaseService.getAllOrderedDishesOfOrderSnapshotListener(companyAccount, order) { orderedDishes ->
                var orderAndDishes = mutableListOf<OrderedDishesHelper>()
                DatabaseService.getDishes(companyAccount) { dishes ->
                    for (orderedDish in orderedDishes) {
                        val dish = dishes.find { d -> d.documentId == orderedDish.dishReference }
                        dish?.let { dishObject ->
                            orderAndDishes.add(OrderedDishesHelper(orderedDish, dishObject))
                        }
                    }
                    viewController.setOrderedDishesOfOrder(orderAndDishes)
                }
            }
        }
    }

    fun setOrderedDishesStatus(orderedDish: OrderedDish, dishesStatus: OrderDishesStatus) {
        var temp = orderedDish
        temp.status = dishesStatus
        DatabaseService.editOrderedDish(temp) { _ -> }
    }
}