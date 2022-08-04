package com.example.ordermatic.controller.view

import android.content.Intent
import android.view.View
import android.widget.*
import com.example.ordermatic.R
import com.example.ordermatic.controller.model.ServiceDashboardModelController
import com.example.ordermatic.controller.model.ServiceDashboardViewControllerInterface
import com.example.ordermatic.model.Order
import com.example.ordermatic.model.User
import com.example.ordermatic.view.activity.GeneralMenuSelectionActivity
import com.example.ordermatic.view.activity.ServiceCreateOrderActivity
import com.example.ordermatic.view.activity.ServiceDashboardActivity
import com.example.ordermatic.view.adapter.ServiceActiveOrdersAdatper
import com.example.ordermatic.view.helper.setImageFromMipmap


class ServiceDashboardViewController(private val activity: ServiceDashboardActivity):
    ServiceDashboardViewControllerInterface {

    private var modelController: ServiceDashboardModelController
    private var user: User

    init {
        modelController = ServiceDashboardModelController(this)
        user = activity.intent.getSerializableExtra("EXTRA_USER") as User

        setUpButtons()
        setUpViewElements()

        modelController.getAllActiveOrders()
    }

    private fun setUpViewElements() {
        val textViewTopBar: TextView = activity.findViewById(R.id.service_home_header)
        textViewTopBar.text = "${user.role.rawValue.capitalize()}-Home"

        val imageViewLogo: ImageView = activity.findViewById(R.id.imageViewLogo)
        imageViewLogo.setImageFromMipmap(R.mipmap.logo_service)

        val imageViewMenu: ImageView = activity.findViewById(R.id.edit_speisekarte_pic)
        imageViewMenu.setImageFromMipmap(R.mipmap.icon_menu)

    }

    private fun setUpButtons() {
        val buttonCreateOrder: Button = activity.findViewById(R.id.Add_Table_Button)
        buttonCreateOrder.setOnClickListener(::onCreateNewOrder)

        val buttonSeeMenu: LinearLayout = activity.findViewById(R.id.Show_Dishes_Button)
        buttonSeeMenu.setOnClickListener(::onSeeMenuClicked)

        val buttonLogout: Button = activity.findViewById(R.id.Logout_Profile_Button)
        buttonLogout.setOnClickListener(::onLogoutClicked)
    }

    private fun onCreateNewOrder(view: View) {
        val intent = Intent(activity, ServiceCreateOrderActivity::class.java)
        intent.putExtra("EXTRA_USER", user)
        activity.startActivity(intent)
    }

    private fun onSeeMenuClicked(view: View) {
        val intent = Intent(activity, GeneralMenuSelectionActivity::class.java)
        activity.startActivity(intent)
    }

    private fun onLogoutClicked(view: View) {
        activity.finish()
    }

    /******************************************
     * Model -> View
     ******************************************/
    override fun setActiveOrders(orders: List<Order>) {
        val listViewAllOpenings: ListView = activity.findViewById(R.id.listView_allOpenings)
        listViewAllOpenings.adapter = ServiceActiveOrdersAdatper(activity, 0, orders, user)
    }
}