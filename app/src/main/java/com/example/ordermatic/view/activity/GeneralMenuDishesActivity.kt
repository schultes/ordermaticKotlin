package com.example.ordermatic.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ordermatic.R
import com.example.ordermatic.controller.view.GeneralMenuDishesViewController

class GeneralMenuDishesActivity : AppCompatActivity() {

    private lateinit var viewController: GeneralMenuDishesViewController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_general_menu_dishes)
        viewController = GeneralMenuDishesViewController(this)
    }
}