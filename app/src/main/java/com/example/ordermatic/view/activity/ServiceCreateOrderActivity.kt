package com.example.ordermatic.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ordermatic.R
import com.example.ordermatic.controller.view.ServiceCreateOrderViewController

class ServiceCreateOrderActivity : AppCompatActivity() {

    private lateinit var viewController: ServiceCreateOrderViewController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_order)
        viewController = ServiceCreateOrderViewController(this)
    }
}