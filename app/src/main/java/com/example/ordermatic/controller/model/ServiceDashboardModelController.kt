package com.example.ordermatic.controller.model

import com.example.ordermatic.model.Dish
import com.example.ordermatic.model.Order
import com.example.ordermatic.model.OrderDishesStatus
import com.example.ordermatic.model.OrderedDish
import com.example.ordermatic.model.authentication.AuthenticationService
import com.example.ordermatic.model.database.DatabaseService

interface ServiceDashboardViewControllerInterface {
    fun setActiveOrders(orders: List<Order>)
}

class ServiceDashboardModelController(private val viewController: ServiceDashboardViewControllerInterface?) {

    fun getAllActiveOrders() {
        viewController?.let { viewControllerObject ->
            AuthenticationService.getCurrentCompanyAccount()?.let { companyAccount ->
                DatabaseService.addOrderSnapshotListener(companyAccount, true) { orders ->
                    viewControllerObject.setActiveOrders(orders)
                }
            }
        }
    }

    fun getReadyOrderedDishesOfOrder(order: Order, callback: (List<OrderedDish>) -> Unit) {
        AuthenticationService.getCurrentCompanyAccount()?.let { companyAccount ->
            DatabaseService.getOrderDishesByStatusSnapshotListener(
                companyAccount,
                order,
                OrderDishesStatus.READY
            ) { readyOrderedDishes ->
                callback(readyOrderedDishes)
            }
        }
    }

    fun getDishOfOrderedDish(orderDish: OrderedDish, callback: (Dish) -> Unit) {
        if(AuthenticationService.getCurrentCompanyAccount() != null) {
            DatabaseService.getDish(orderDish.dishReference) { dish ->
                dish?.let { dishObject ->
                    callback(dishObject)
                }
            }
        }
    }

}