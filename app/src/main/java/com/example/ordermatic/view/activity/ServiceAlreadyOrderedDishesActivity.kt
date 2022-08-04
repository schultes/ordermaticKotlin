package com.example.ordermatic.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ordermatic.R
import com.example.ordermatic.controller.view.ServiceAlreadyOrderedDishesViewController

class ServiceAlreadyOrderedDishesActivity : AppCompatActivity() {

    private lateinit var viewController: ServiceAlreadyOrderedDishesViewController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_already_ordered_dishes)
        viewController = ServiceAlreadyOrderedDishesViewController(this)
    }
}