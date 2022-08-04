package com.example.ordermatic.controller.view

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.example.ordermatic.R
import com.example.ordermatic.controller.model.KitchenBarActiveOrdersModelController
import com.example.ordermatic.controller.model.KitchenBarActiveOrdersViewControllerInterface
import com.example.ordermatic.model.Order
import com.example.ordermatic.model.OrderDishesStatus
import com.example.ordermatic.model.User
import com.example.ordermatic.model.UserRole
import com.example.ordermatic.view.activity.KitchenBarActiveOrdersActivity
import com.example.ordermatic.view.adapter.KitchenBarActiveOrderAdapter
import com.example.ordermatic.view.helper.setImageFromMipmap


class KitchenBarActiveOrdersViewController(private val activity: KitchenBarActiveOrdersActivity):
    KitchenBarActiveOrdersViewControllerInterface {

    private var modelController: KitchenBarActiveOrdersModelController
    private var user: User
    private var changedOrders: List<Order>
    private var matchedOrders: List<Order>

    init {
        modelController = KitchenBarActiveOrdersModelController(this)
        user = activity.intent.getSerializableExtra("EXTRA_USER") as User
        changedOrders = arrayListOf()
        matchedOrders = arrayListOf()

        setLogoAndTopBar()
        getActiveOrders()

        val sendButton: Button = activity.findViewById(R.id.send_button)
        sendButton.setOnClickListener(::sendStatusOfOrders)
    }

    private fun setLogoAndTopBar() {
        val imageViewLogoIcon: ImageView = activity.findViewById(R.id.imageViewLogo)
        if (user.role == UserRole.KITCHEN) {
            imageViewLogoIcon.setImageFromMipmap(R.mipmap.logo_kitchen)
        } else { // bar
            imageViewLogoIcon.setImageFromMipmap(R.mipmap.logo_bar)
        }

        val textViewTopBar: TextView = activity.findViewById(R.id.kb_activeorders_header)
        textViewTopBar.text = "Bestellungen"
    }

    fun onCheckboxClicked(orderReference: String, isChecked: Boolean) {
        val checkedOrder = matchedOrders.find { order -> order.documentId!! == orderReference }
        if (isChecked) {
            (changedOrders as ArrayList).add(checkedOrder!!)
        } else {
            (changedOrders as ArrayList).remove(checkedOrder!!)
        }
    }


    /******************************************
     * View -> Model
     ******************************************/
    private fun getActiveOrders() {
        modelController.getActiveOrders(user)
    }

    private fun sendStatusOfOrders(view: View) {
        if (changedOrders.isNotEmpty()) {
            val orderIds = changedOrders.map { order -> order.documentId!! }
            modelController.setStatusOfAllOrderedDishes(orderIds, OrderDishesStatus.READY, user)
            changedOrders = arrayListOf()
        }
    }


    /******************************************
     * Model -> View
     ******************************************/
    override fun setActiveOrders(matchedOrders: List<Order>) {
        this.matchedOrders = matchedOrders
        val tableList: ListView = activity.findViewById(R.id.renderedTables)
        tableList.adapter = KitchenBarActiveOrderAdapter(activity, 0, matchedOrders, user, this)
    }


}