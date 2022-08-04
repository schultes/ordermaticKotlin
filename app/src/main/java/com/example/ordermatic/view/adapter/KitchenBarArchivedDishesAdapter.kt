package com.example.ordermatic.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.ordermatic.R
import com.example.ordermatic.model.helper.OrderedDishesHelper

class KitchenBarArchivedDishesAdapter(context: Context, ressource: Int, orders: List<OrderedDishesHelper>) :
    ArrayAdapter<OrderedDishesHelper>(context, ressource, orders) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        view = LayoutInflater.from(context).inflate(R.layout.list_item_kitchen_bar_archived_ordered_dishes, parent, false)
        val item: OrderedDishesHelper? = getItem(position)

        val dishName = view.findViewById<View>(R.id.olddishes_dishestable) as TextView
        dishName.text = item!!.dish.name


        return view
    }
}