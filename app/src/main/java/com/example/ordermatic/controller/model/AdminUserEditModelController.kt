package com.example.ordermatic.controller.model

import com.example.ordermatic.model.User
import com.example.ordermatic.model.database.DatabaseService

interface AdminUserEditViewControllerInterface {
    fun onTransactionResult()
}

class AdminUserEditModelController(private val viewController: AdminUserEditViewControllerInterface) {
    fun editUser(user: User) {
        DatabaseService.editUser(user) { error ->
            if (error == null)
                viewController.onTransactionResult()
        }
    }

    fun deleteUser(user: User) {
        DatabaseService.deleteUser(user) { error ->
            if (error == null)
                viewController.onTransactionResult()
        }
    }
}