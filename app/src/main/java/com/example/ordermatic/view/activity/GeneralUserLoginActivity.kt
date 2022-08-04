package com.example.ordermatic.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ordermatic.R
import com.example.ordermatic.controller.view.GeneralUserLoginViewController

class GeneralUserLoginActivity : AppCompatActivity() {

    private lateinit var viewController: GeneralUserLoginViewController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_general_user_login)
        viewController = GeneralUserLoginViewController(this)
    }
}