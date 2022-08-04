package com.example.ordermatic.controller.model

import com.example.ordermatic.model.User
import com.example.ordermatic.model.authentication.AuthenticationService
import com.example.ordermatic.model.database.DatabaseService

interface AdminUsersOverviewViewControllerInterface {
    fun setUsers(users: List<User>)
}

class AdminUsersOverviewModelController(private val viewController: AdminUsersOverviewViewControllerInterface) {
    fun getUsers() {
        AuthenticationService.getCurrentCompanyAccount()?.let { companyAccount ->
            DatabaseService.addOtherUsersSnapshotListener(companyAccount) { users ->
                viewController.setUsers(users)
            }
        }
    }
}