package com.example.ordermatic.view.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.ordermatic.R
import com.example.ordermatic.model.User
import com.example.ordermatic.model.UserRole
import com.example.ordermatic.view.helper.setImageFromMipmap

class KitchenBarDashboardActivity : Activity() {

    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kitchen_bar_dashboard)
        user = intent.getSerializableExtra("EXTRA_USER") as User

        setImageViews()

        val textViewTopBar: TextView = findViewById(R.id.kb_home_header)
        textViewTopBar.text = "${user.role.rawValue.capitalize()}-Home"
    }

    fun setImageViews() {
        val imageViewLogoIcon: ImageView = findViewById(R.id.imageViewLogo)
        if (user.role == UserRole.KITCHEN) {
            imageViewLogoIcon.setImageFromMipmap(R.mipmap.logo_kitchen)
        } else { // bar
            imageViewLogoIcon.setImageFromMipmap(R.mipmap.logo_bar)
        }

        val imageViewActiveOrder: ImageView = findViewById(R.id.aktuelle_bestellungen_icon)
        imageViewActiveOrder.setImageFromMipmap(R.mipmap.icon_order_current)

        val imageViewArchivedOrder: ImageView = findViewById(R.id.abgeschlossene_bestellungen_icon)
        imageViewArchivedOrder.setImageFromMipmap(R.mipmap.icon_order_completed)
    }

    fun onLogoutClicked(view: View) {
        finish()
    }

    fun onSeeActiveOrders(view: View) {
        val intent = Intent(this, KitchenBarActiveOrdersActivity::class.java)
            .putExtra("EXTRA_USER", user)
        startActivity(intent)
    }

    fun onSeeArchievedOrders(view: View) {
        val intent = Intent(this, KitchenBarArchivedOrdersActivity::class.java)
            .putExtra("EXTRA_USER", user)
        startActivity(intent)
    }
}