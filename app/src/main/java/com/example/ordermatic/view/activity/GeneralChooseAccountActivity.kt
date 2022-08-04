package com.example.ordermatic.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ordermatic.R
import com.example.ordermatic.controller.view.GeneralChooseAccountViewController

class GeneralChooseAccountActivity : AppCompatActivity() {

    private lateinit var viewController: GeneralChooseAccountViewController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_general_choose_account)
        viewController = GeneralChooseAccountViewController(this)
    }
}