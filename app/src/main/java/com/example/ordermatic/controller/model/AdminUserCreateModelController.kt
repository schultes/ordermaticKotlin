package com.example.ordermatic.controller.model

import com.example.ordermatic.model.User
import com.example.ordermatic.model.database.DatabaseService

interface AdminUserCreateViewControllerInterface {
    fun onTransactionResult()
}

class AdminUserCreateModelController(private val viewController: AdminUserCreateViewControllerInterface) {
    fun addUser(user: User) {
        DatabaseService.addUser(user) { userObject ->
            if (userObject != null) {
                viewController.onTransactionResult()
            }
        }
    }
}