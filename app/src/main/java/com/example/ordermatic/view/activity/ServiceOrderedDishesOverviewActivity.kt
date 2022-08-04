package com.example.ordermatic.view.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.example.ordermatic.R
import com.example.ordermatic.controller.view.ServiceOrderedDishesOverviewViewController

class ServiceOrderedDishesOverviewActivity : Activity() {

    private lateinit var viewController: ServiceOrderedDishesOverviewViewController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_order_menu)
        viewController = ServiceOrderedDishesOverviewViewController(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 204) {
            viewController.onActivityResult(data)
        } else if (resultCode == 4792506) {
            data?.let { dataObject ->
                if (dataObject.getBooleanExtra("SHOULD_FINISH", false))
                    finish()
            }
        }
    }
}