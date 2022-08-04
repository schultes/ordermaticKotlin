package com.example.ordermatic.controller.model

import com.example.ordermatic.model.authentication.AuthenticationService

interface AuthenticationBusinessLoginViewControllerInterface {
    fun onLoginResult(error: String?)
}

class AuthenticationBusinessLoginModelController(private val viewController: AuthenticationBusinessLoginViewControllerInterface) {

    /******************************************
     * View -> Model
     ******************************************/

    fun onLoginClicked(email: String, password: String) {
        AuthenticationService.signInToCompanyAccount(email, password) { _, error ->
            viewController.onLoginResult(error)
        }
    }
}