package com.example.ordermatic.controller.view

import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.example.ordermatic.R
import com.example.ordermatic.controller.model.GeneralMenuDishesModelController
import com.example.ordermatic.controller.model.GeneralMenuDishesViewControllerInterface
import com.example.ordermatic.model.Dish
import com.example.ordermatic.model.DishesType
import com.example.ordermatic.view.activity.GeneralMenuDishesActivity
import com.example.ordermatic.view.adapter.GeneralMenuDishesAdapter
import com.example.ordermatic.view.helper.setImageFromMipmap

class GeneralMenuDishesViewController(private val activity: GeneralMenuDishesActivity):
    GeneralMenuDishesViewControllerInterface {

    private var modelController: GeneralMenuDishesModelController

    private var selectedDishesType: DishesType? = null

    private var textViewHeader: TextView
    private var listViewDishes: ListView

    init {
        modelController = GeneralMenuDishesModelController(this)

        setImageViews()

        selectedDishesType = activity.intent.getSerializableExtra("EXTRA_SELECTED_DISHES_TYPE") as DishesType

        textViewHeader = activity.findViewById(R.id.textViewHeader)
        listViewDishes = activity.findViewById(R.id.listViewDishes)

        textViewHeader.text = selectedDishesType!!.rawValue
        modelController.getDishes(selectedDishesType!!)
    }

    private fun setImageViews() {
        val imageViewLogoNeutral: ImageView = activity.findViewById(R.id.imageViewLogo)
        imageViewLogoNeutral.setImageFromMipmap(R.mipmap.logo_neutral)
    }

    /******************************************
     * Model -> View
     ******************************************/

    override fun setDishes(dishes: List<Dish>) {
        listViewDishes.adapter = GeneralMenuDishesAdapter(activity, 0, dishes)
    }
}