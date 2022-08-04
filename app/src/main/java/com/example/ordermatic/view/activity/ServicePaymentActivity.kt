package com.example.ordermatic.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ordermatic.R
import com.example.ordermatic.controller.view.ServicePaymentViewController

class ServicePaymentActivity : AppCompatActivity() {

    private lateinit var viewController: ServicePaymentViewController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        viewController = ServicePaymentViewController(this)
    }
}