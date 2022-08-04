package com.example.ordermatic.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ordermatic.R
import com.example.ordermatic.controller.view.AdminUserCreateViewController

class AdminUserCreateActivity : AppCompatActivity() {

    private lateinit var viewController: AdminUserCreateViewController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_user_create)
        viewController = AdminUserCreateViewController(this)
    }
}