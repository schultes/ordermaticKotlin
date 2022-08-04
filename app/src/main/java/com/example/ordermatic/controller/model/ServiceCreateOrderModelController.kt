package com.example.ordermatic.controller.model

import com.example.ordermatic.model.Order
import com.example.ordermatic.model.authentication.AuthenticationService
import com.example.ordermatic.model.database.DatabaseService

interface ServiceCreateOrderViewControllerInterface {
    fun showOrderDetails(order: Order)
    fun showError()
}

class ServiceCreateOrderModelController(private val viewController: ServiceCreateOrderViewControllerInterface) {

    fun addOrder(tableNumber: String, userReference: String, date: String) {
        AuthenticationService.getCurrentCompanyAccount()?.let { companyAccount ->
            val companyReference = companyAccount.id
            // Can only create new order if tableNumber does not exist already (active)
            DatabaseService.getActiveOrderByTableNumber(companyAccount, tableNumber) { order ->
                if (order != null) {
                    viewController.showError()
                }
                if (order == null) {
                    val temp = Order(
                        companyReference,
                        userReference,
                        tableNumber,
                        true,
                        date
                    )
                    DatabaseService.addOrder(temp) { result ->
                        result?.let { resultObject ->
                            viewController.showOrderDetails(resultObject)
                        }
                        if (result == null) {
                            viewController.showError()
                        }
                    }
                }
            }
        }
    }
}