package com.example.ordermatic.controller.model

import com.example.ordermatic.model.Company
import com.example.ordermatic.model.User
import com.example.ordermatic.model.UserRole
import com.example.ordermatic.model.authentication.AuthenticationService
import com.example.ordermatic.model.database.DatabaseService

interface AuthenticationBusinessRegisterViewControllerInterface {
    fun onRegisterResult(error: String?)
}

class AuthenticationBusinessRegisterModelController(private val viewController: AuthenticationBusinessRegisterViewControllerInterface) {
    fun onRegisterClicked(
        companyName: String,
        companyAddressFirstLine: String,
        companyAddressSecondLine: String,
        companyPassword: String,
        adminName: String,
        adminEmail: String,
        adminPassword: String
    ) {
        AuthenticationService.createCompanyAccount(adminEmail, companyPassword) { companyAccount, error ->
            companyAccount?.let { companyAccountObject ->
                val company = Company(companyName, companyAddressFirstLine, companyAddressSecondLine, companyAccountObject.id)
                val user = User(companyAccountObject.id, adminName, adminPassword, UserRole.ADMIN)
                DatabaseService.setCompany(companyAccountObject.id, company) { error ->
                    if (error == null) {
                        DatabaseService.addUser(user) { user ->
                            if (user == null)
                                viewController.onRegisterResult("Error while adding admin!")
                            else
                                viewController.onRegisterResult(null)
                        }
                    } else {
                        viewController.onRegisterResult(error)
                    }
                }
            }
            if (companyAccount == null)
                viewController.onRegisterResult(error)
        }
    }
}