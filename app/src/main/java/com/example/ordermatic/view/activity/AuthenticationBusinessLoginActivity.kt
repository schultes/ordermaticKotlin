package com.example.ordermatic.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ordermatic.R
import com.example.ordermatic.controller.view.AuthenticationBusinessLoginViewController
import com.example.ordermatic.model.authentication.AuthenticationService

class AuthenticationBusinessLoginActivity : AppCompatActivity() {

    private lateinit var viewController: AuthenticationBusinessLoginViewController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication_business_login)
        viewController = AuthenticationBusinessLoginViewController(this)
    }

    override fun onResume() {
        super.onResume()

        AuthenticationService.getCurrentCompanyAccount()?.let {
            startActivity(Intent(this, GeneralChooseAccountActivity::class.java))
            finish()
        }
    }
}