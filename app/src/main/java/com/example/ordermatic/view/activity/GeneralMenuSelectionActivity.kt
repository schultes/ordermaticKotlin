package com.example.ordermatic.view.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.example.ordermatic.R
import com.example.ordermatic.model.DishesType
import com.example.ordermatic.view.helper.setImageFromMipmap

class GeneralMenuSelectionActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_general_menu_selection)
        setImageViews()
    }

    private fun setImageViews() {
        val imageViewLogoNeutral: ImageView = findViewById(R.id.logo_neutral)
        imageViewLogoNeutral.setImageFromMipmap(R.mipmap.logo_neutral)

        val imageViewdrinksIcon: ImageView = findViewById(R.id.getraenke_icon)
        imageViewdrinksIcon.setImageFromMipmap(R.mipmap.icon_dishes_drinks)

        val imageViewStarterIcon: ImageView = findViewById(R.id.vorspeisen_pic)
        imageViewStarterIcon.setImageFromMipmap(R.mipmap.icon_dishes_starters)

        val imageViewMainDishesIcon: ImageView = findViewById(R.id.hauptspeisen_pic)
        imageViewMainDishesIcon.setImageFromMipmap(R.mipmap.icon_dishes_main_dishes)

        val imageViewDessertIcon: ImageView = findViewById(R.id.desserts_pic)
        imageViewDessertIcon.setImageFromMipmap(R.mipmap.icon_dishes_desserts)
    }


    fun onDrinksClicked(view: View) {
        startActivity(
            Intent(this, GeneralMenuDishesActivity::class.java).putExtra(
                "EXTRA_SELECTED_DISHES_TYPE",
                DishesType.DRINKS
            )
        )
    }

    fun onStartersClicked(view: View) {
        startActivity(
            Intent(this, GeneralMenuDishesActivity::class.java).putExtra(
                "EXTRA_SELECTED_DISHES_TYPE",
                DishesType.STARTERS
            )
        )
    }

    fun onMainDishesClicked(view: View) {
        startActivity(
            Intent(this, GeneralMenuDishesActivity::class.java).putExtra(
                "EXTRA_SELECTED_DISHES_TYPE",
                DishesType.MAIN_DISHES
            )
        )
    }

    fun onDessertsClicked(view: View) {
        startActivity(
            Intent(this, GeneralMenuDishesActivity::class.java).putExtra(
                "EXTRA_SELECTED_DISHES_TYPE",
                DishesType.DESSERTS
            )
        )
    }
}