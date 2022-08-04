package com.example.ordermatic.view.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import com.example.ordermatic.R
import com.example.ordermatic.model.Order
import com.example.ordermatic.model.User
import com.example.ordermatic.view.activity.KitchenBarArchivedDishesActivity

class KitchenBarArchivedOrderAdapter(context: Context, ressource: Int, orders: List<Order>, user: User) :
    ArrayAdapter<Order>(context, ressource, orders) {
    val user: User = user

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        view = LayoutInflater.from(context).inflate(R.layout.list_item_kitchen_bar_archived_orders, parent, false)
        val item = getItem(position)

        val button = view.findViewById<View>(R.id.olddishes_tablebutton) as Button
        button.text = "Tischnummer: ${item?.tableNumber}"
        button.setOnClickListener {
            val intent = Intent(this.context, KitchenBarArchivedDishesActivity::class.java)
            intent.putExtra("EXTRA_ORDER", item!!)
            intent.putExtra("EXTRA_USER", user)
            context.startActivity(intent)
        }
        return view
    }
}