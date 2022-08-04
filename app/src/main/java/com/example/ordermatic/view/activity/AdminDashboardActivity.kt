package com.example.ordermatic.view.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.example.ordermatic.R
import com.example.ordermatic.model.DishesType
import com.example.ordermatic.view.helper.setImageFromMipmap


class AdminDashboardActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashboard)
        setImageViews()
    }

    fun onEditDrinksClicked(v: View) {
        startActivity(
            Intent(this, AdminDishesOverviewActivity::class.java)
                .putExtra("EXTRA_SELECTED_DISHES_TYPE", DishesType.DRINKS)
        )
    }

    fun onEditStartersClicked(v: View) {
        startActivity(
            Intent(this, AdminDishesOverviewActivity::class.java)
                .putExtra("EXTRA_SELECTED_DISHES_TYPE", DishesType.STARTERS)
        )
    }

    fun onEditMainDishesClicked(v: View) {
        startActivity(
            Intent(this, AdminDishesOverviewActivity::class.java)
                .putExtra("EXTRA_SELECTED_DISHES_TYPE", DishesType.MAIN_DISHES)
        )
    }

    fun onEditDessertsClicked(v: View) {
        startActivity(
            Intent(this, AdminDishesOverviewActivity::class.java)
                .putExtra("EXTRA_SELECTED_DISHES_TYPE", DishesType.DESSERTS)
        )
    }

    fun onUserOverviewClicked(v: View) {
        startActivity(
            Intent(this, AdminUsersOverviewActivity::class.java)
        )
    }

    fun onSeeDishesClicked(v: View) {
        startActivity(
            Intent(this, GeneralMenuSelectionActivity::class.java)
        )
    }

    fun onLogoutClicked(v: View) {
        finish()
    }

    private fun setImageViews() {
        val imageViewLogoAdmin: ImageView = findViewById(R.id.imageViewLogo)
        imageViewLogoAdmin.setImageFromMipmap(R.mipmap.logo_admin)

        val imageViewIconPerson: ImageView = findViewById(R.id.person_icon)
        imageViewIconPerson.setImageFromMipmap(R.mipmap.icon_person)

        val imageViewIconDrinks: ImageView = findViewById(R.id.getraenke_icon)
        imageViewIconDrinks.setImageFromMipmap(R.mipmap.icon_dishes_drinks)

        val imageViewIconStarters: ImageView = findViewById(R.id.vorspeisen_pic)
        imageViewIconStarters.setImageFromMipmap(R.mipmap.icon_dishes_starters)

        val imageViewIconDesserts: ImageView = findViewById(R.id.desserts_pic)
        imageViewIconDesserts.setImageFromMipmap(R.mipmap.icon_dishes_desserts)

        val imageViewIconMainDishes: ImageView = findViewById(R.id.hauptspeisen_pic)
        imageViewIconMainDishes.setImageFromMipmap(R.mipmap.icon_dishes_main_dishes)

        val imageViewIconMenu: ImageView = findViewById(R.id.edit_speisekarte_pic)
        imageViewIconMenu.setImageFromMipmap(R.mipmap.icon_menu)
    }
}