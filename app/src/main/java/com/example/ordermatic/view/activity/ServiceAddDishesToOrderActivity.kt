package com.example.ordermatic.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ordermatic.R
import com.example.ordermatic.controller.view.ServiceAddDishesToOrderViewController
import com.example.ordermatic.model.Dish
import com.example.ordermatic.view.helper.OnItemClicked

class ServiceAddDishesToOrderActivity : AppCompatActivity(), OnItemClicked {

    private lateinit var viewController: ServiceAddDishesToOrderViewController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_add_dish_to_order)
        viewController = ServiceAddDishesToOrderViewController(this)
    }

    override fun onClick(value: Dish) {
        viewController.addDishToUnsavedOrder(value)
    }

    override fun onRemove(value: Dish) {
        viewController.removeDishFromUnsavedOrder(value)
    }
}