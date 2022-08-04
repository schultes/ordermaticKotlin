package com.example.ordermatic.controller.model

import com.example.ordermatic.model.Company
import com.example.ordermatic.model.User
import com.example.ordermatic.model.authentication.AuthenticationService
import com.example.ordermatic.model.database.DatabaseService

interface GeneralChooseAccountViewControllerInterface {
    fun setCompanyName(company: Company)
    fun setChooseAccountList(users: List<User>)
}

class GeneralChooseAccountModelController(private val viewController: GeneralChooseAccountViewControllerInterface) {
    fun getCompanyName() {
        AuthenticationService.getCurrentCompanyAccount()?.let { companyAccount ->
            DatabaseService.getCompany(companyAccount.id) { company ->
                company?.let { companyObject ->
                    viewController.setCompanyName(companyObject)
                }
            }
        }
    }

    fun getChooseAcccountList() {
        AuthenticationService.getCurrentCompanyAccount()?.let { companyAccount ->
            DatabaseService.addUsersSnapshotListener(companyAccount) { users ->
                viewController.setChooseAccountList(users)
            }
        }
    }
}