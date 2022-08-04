package com.example.ordermatic.view.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import com.example.ordermatic.R
import com.example.ordermatic.controller.view.KitchenBarActiveOrdersViewController
import com.example.ordermatic.model.Order
import com.example.ordermatic.model.User
import com.example.ordermatic.view.activity.KitchenBarActiveDishesActivity
import com.example.ordermatic.view.helper.formatDate

class KitchenBarActiveOrderAdapter(
    context: Context,
    ressource: Int,
    orders: List<Order>,
    user: User,
    viewController: KitchenBarActiveOrdersViewController
) : ArrayAdapter<Order>(context, ressource, orders) {

    val user: User = user
    val viewController: KitchenBarActiveOrdersViewController = viewController

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        view = LayoutInflater.from(context).inflate(R.layout.list_item_kitchen_bar_active_orders, parent, false)
        val item = getItem(position)

        val button = view.findViewById<View>(R.id.active_table_button) as Button
        button.text = "Tischnummer: ${item!!.tableNumber}\n${item.createdAt.formatDate()}"
        button.setOnClickListener {
            val intent = Intent(this.context, KitchenBarActiveDishesActivity::class.java)
            intent.putExtra("EXTRA_ORDER", item)
            intent.putExtra("EXTRA_USER", user)
            context.startActivity(intent)
        }

        val cbOfButton = view.findViewById<View>(R.id.active_table_checkBox) as CheckBox
        cbOfButton.setOnClickListener { view ->
            if (view is CheckBox) {
                viewController.onCheckboxClicked(item.documentId!!, view.isChecked)
            }
        }

        return view
    }


}