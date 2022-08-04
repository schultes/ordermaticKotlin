package com.example.ordermatic.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.TextView
import com.example.ordermatic.R
import com.example.ordermatic.controller.view.KitchenBarActiveDishesViewController
import com.example.ordermatic.model.helper.OrderedDishesHelper

class KitchenBarActiveDishesAdapter(
    context: Context,
    ressource: Int,
    orders: List<OrderedDishesHelper>,
    viewController: KitchenBarActiveDishesViewController
) : ArrayAdapter<OrderedDishesHelper>(context, ressource, orders) {

    val viewController: KitchenBarActiveDishesViewController = viewController

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        view = LayoutInflater.from(context).inflate(R.layout.list_item_kitchen_bar_active_ordered_dishes, parent, false)
        val item: OrderedDishesHelper? = getItem(position)

        val dishName = view.findViewById<View>(R.id.active_dish_name) as TextView
        dishName.text = item!!.dish.name

        val cbOfDishButton = view.findViewById<View>(R.id.active_dish_checkBox) as CheckBox
        cbOfDishButton.setOnClickListener { view ->
            if (view is CheckBox) {
                viewController.onCheckboxClicked(item.orderedDish.documentId!!, view.isChecked)
            }
        }

        return view
    }
}