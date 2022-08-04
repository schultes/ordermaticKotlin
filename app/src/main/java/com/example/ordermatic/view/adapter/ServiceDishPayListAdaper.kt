package com.example.ordermatic.view.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.ordermatic.R
import com.example.ordermatic.model.OrderDishesStatus
import com.example.ordermatic.model.helper.OrderedDishesHelper
import com.example.ordermatic.view.helper.SetOrderedDishesStatusInterface

class ServiceDishPayListAdaper(
    val context: Context,
    val orderedDishes: List<OrderedDishesHelper>,
    val viewController: SetOrderedDishesStatusInterface
) : BaseAdapter() {

    override fun getCount(): Int {
        return orderedDishes.size
    }

    override fun getItem(position: Int): OrderedDishesHelper {
        return orderedDishes[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        view = LayoutInflater.from(context).inflate(R.layout.list_item_general_payment, parent, false)

        val dishArray = orderedDishes

        val name: TextView = view.findViewById(R.id.textView_dishName)
        name.text = getItem(position).dish.name
        val price: TextView = view.findViewById(R.id.textView_dishPrice)
        price.text = "${getItem(position).dish.price} €"

        if (getItem(position).orderedDish.status == OrderDishesStatus.DONE) {
            name.setTextColor(Color.parseColor("#719e62")) // green
        } else if (getItem(position).orderedDish.status == OrderDishesStatus.READY) {
            name.setTextColor(Color.parseColor("#577091"))
        } else {
            name.setTextColor(Color.parseColor("#a55151"))
        }

        view.setOnClickListener {
            if (getItem(position).orderedDish.status == OrderDishesStatus.READY)
                viewController.setOrderedDishesStatus(getItem(position).orderedDish, OrderDishesStatus.DONE)
        }

        return view
    }
}