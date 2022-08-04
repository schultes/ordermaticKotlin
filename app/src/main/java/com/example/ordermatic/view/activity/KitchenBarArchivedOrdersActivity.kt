package com.example.ordermatic.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ordermatic.R
import com.example.ordermatic.controller.view.KitchenBarArchivedOrdersViewController

class KitchenBarArchivedOrdersActivity : AppCompatActivity() {

    private lateinit var viewController: KitchenBarArchivedOrdersViewController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kitchen_bar_archived_orders)
        viewController = KitchenBarArchivedOrdersViewController(this)
    }
}