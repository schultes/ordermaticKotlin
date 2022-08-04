package com.example.ordermatic.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ordermatic.R
import com.example.ordermatic.controller.view.AuthenticationBusinessRegisterViewController

class AuthenticationBusinessRegisterActivity : AppCompatActivity() {

    private lateinit var viewController: AuthenticationBusinessRegisterViewController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication_business_register)
        viewController = AuthenticationBusinessRegisterViewController(this)
    }
}