package com.example.ordermatic.controller.view

import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.example.ordermatic.R
import com.example.ordermatic.controller.model.KitchenBarArchivedOrdersModelController
import com.example.ordermatic.controller.model.KitchenBarArchivedOrdersViewControllerInterface
import com.example.ordermatic.model.Order
import com.example.ordermatic.model.User
import com.example.ordermatic.model.UserRole
import com.example.ordermatic.view.activity.KitchenBarArchivedOrdersActivity
import com.example.ordermatic.view.adapter.KitchenBarArchivedOrderAdapter
import com.example.ordermatic.view.helper.setImageFromMipmap

class KitchenBarArchivedOrdersViewController(private val activity: KitchenBarArchivedOrdersActivity):
    KitchenBarArchivedOrdersViewControllerInterface {

    private var modelController: KitchenBarArchivedOrdersModelController
    private var user: User

    init {
        modelController = KitchenBarArchivedOrdersModelController(this)
        user = activity.intent.getSerializableExtra("EXTRA_USER") as User

        setBasicViews()
        getArchivedOrders()
    }

    private fun setBasicViews() {
        val imageViewLogoIcon: ImageView = activity.findViewById(R.id.imageViewLogo)
        if (user.role == UserRole.KITCHEN) {
            imageViewLogoIcon.setImageFromMipmap(R.mipmap.logo_kitchen)
        } else { // bar
            imageViewLogoIcon.setImageFromMipmap(R.mipmap.logo_bar)
        }
        val textViewTopBar: TextView = activity.findViewById(R.id.kb_oldorders_header)
        val bartext = "${user.role.rawValue} - Bestellungsarchiv"
        textViewTopBar.text = bartext
    }

    /******************************************
     * View -> Model
     ******************************************/
    private fun getArchivedOrders() {
        modelController.getArchivedOrders(user)
    }

    /******************************************
     * Model -> View
     ******************************************/
    override fun setArchivedOrders(archivedOrders: List<Order>) {
        val tableList: ListView = activity.findViewById(R.id.oldtable_orders)
        tableList.adapter = KitchenBarArchivedOrderAdapter(activity, 0, archivedOrders, user)
    }
}