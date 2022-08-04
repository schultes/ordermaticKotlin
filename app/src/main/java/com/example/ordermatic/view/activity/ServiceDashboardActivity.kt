package com.example.ordermatic.view.activity

import android.app.Activity
import android.os.Bundle
import com.example.ordermatic.R
import com.example.ordermatic.controller.view.ServiceDashboardViewController

class ServiceDashboardActivity : Activity() {

    private lateinit var viewController: ServiceDashboardViewController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_dashboard)
        viewController = ServiceDashboardViewController(this)
    }
}