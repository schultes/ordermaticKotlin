package com.example.ordermatic.controller.view

import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.example.ordermatic.R
import com.example.ordermatic.controller.model.AdminDishesOverviewModelController
import com.example.ordermatic.controller.model.AdminDishesOverviewViewControllerInterface
import com.example.ordermatic.model.Dish
import com.example.ordermatic.model.DishesType
import com.example.ordermatic.view.activity.AdminDishEditActivity
import com.example.ordermatic.view.activity.AdminDishesOverviewActivity
import com.example.ordermatic.view.adapter.AdminDishesEditAdapter
import com.example.ordermatic.view.helper.setImageFromMipmap
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AdminDishesOverviewViewController(private val activity: AdminDishesOverviewActivity):
    AdminDishesOverviewViewControllerInterface {

    private var modelController: AdminDishesOverviewModelController

    private var selectedDishesType: DishesType

    private var textViewSelectedDishesType: TextView
    private var listViewDishes: ListView
    private var floatingActionButtonCreateDishes: FloatingActionButton

    init {
        modelController = AdminDishesOverviewModelController(this)

        setImageViews()

        selectedDishesType = activity.intent.getSerializableExtra("EXTRA_SELECTED_DISHES_TYPE") as DishesType

        textViewSelectedDishesType = activity.findViewById(R.id.textViewSelectedDishesType)
        listViewDishes = activity.findViewById(R.id.listViewDishes)
        floatingActionButtonCreateDishes = activity.findViewById(R.id.floatingActionButtonCreateDishes)
        floatingActionButtonCreateDishes.setOnClickListener(::onCreateDishesClicked)

        modelController.getDishes(selectedDishesType)
    }

    private fun setImageViews() {
        val imageViewLogoAdmin: ImageView = activity.findViewById(R.id.imageViewLogo)
        imageViewLogoAdmin.setImageFromMipmap(R.mipmap.logo_admin)
    }


    /******************************************
     * View -> Model
     ******************************************/

    private fun onCreateDishesClicked(v: View) {
        activity.startActivity(
            Intent(activity, AdminDishEditActivity::class.java)
                .putExtra("EXTRA_SELECTED_DISHES_TYPE", selectedDishesType)
        )
    }

    /******************************************
     * Model -> View
     ******************************************/

    override fun setDishes(dishes: List<Dish>) {
        listViewDishes.adapter = AdminDishesEditAdapter(activity, 0, dishes)
    }
}