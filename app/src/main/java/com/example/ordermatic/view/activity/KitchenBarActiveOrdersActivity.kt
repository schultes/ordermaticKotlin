package com.example.ordermatic.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ordermatic.R
import com.example.ordermatic.controller.view.KitchenBarActiveOrdersViewController

class KitchenBarActiveOrdersActivity : AppCompatActivity() {

    private lateinit var viewController: KitchenBarActiveOrdersViewController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kitchen_bar_active_orders)
        viewController = KitchenBarActiveOrdersViewController(this)
    }
}