package com.example.ordermatic.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ordermatic.R
import com.example.ordermatic.controller.view.AdminUserEditViewController

class AdminUserEditActivity : AppCompatActivity() {

    private lateinit var viewController: AdminUserEditViewController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_user_edit)
        viewController = AdminUserEditViewController(this)
    }
}