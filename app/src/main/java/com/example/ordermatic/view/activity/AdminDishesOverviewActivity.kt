package com.example.ordermatic.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ordermatic.R
import com.example.ordermatic.controller.view.AdminDishesOverviewViewController

class AdminDishesOverviewActivity : AppCompatActivity() {

    private lateinit var viewController: AdminDishesOverviewViewController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dishes_overview)
        viewController = AdminDishesOverviewViewController(this)
    }
}