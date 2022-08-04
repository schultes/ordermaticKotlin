package com.example.ordermatic.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ordermatic.R
import com.example.ordermatic.controller.view.AdminUsersOverviewViewController

class AdminUsersOverviewActivity : AppCompatActivity() {

    private lateinit var viewController: AdminUsersOverviewViewController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_users_overview)
        viewController = AdminUsersOverviewViewController(this)
    }
}