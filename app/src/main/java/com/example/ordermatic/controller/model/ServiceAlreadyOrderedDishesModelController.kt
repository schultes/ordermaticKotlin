package com.example.ordermatic.controller.model

import com.example.ordermatic.model.Order
import com.example.ordermatic.model.OrderDishesStatus
import com.example.ordermatic.model.OrderedDish
import com.example.ordermatic.model.authentication.AuthenticationService
import com.example.ordermatic.model.database.DatabaseService
import com.example.ordermatic.model.helper.OrderedDishesHelper

interface ServiceAlreadyOrderedDishesViewControllerInterface {
    fun setOrderedDishes(orderedDishesList: List<OrderedDishesHelper>)
}

class ServiceAlreadyOrderedDishesModelController(private val viewController: ServiceAlreadyOrderedDishesViewControllerInterface) {

    fun getOrderedDishesOfActiveOrder(order: Order) {
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
                    viewController.setOrderedDishes(orderAndDishes)
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