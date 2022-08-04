package com.example.ordermatic.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.ordermatic.R
import com.example.ordermatic.model.Dish

class GeneralMenuDishesAdapter(context: Context, ressource: Int, dishes: List<Dish>) :
    ArrayAdapter<Dish>(context, ressource, dishes) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var element = convertView
        val inflater = LayoutInflater.from(context)
        val dish = getItem(position)

        if (dish != null) {
            element = inflater.inflate(R.layout.list_item_general_menu_dishes, null)

            val textViewName = element.findViewById<TextView>(R.id.textViewName)
            val textViewPrice = element.findViewById<TextView>(R.id.textViewPrice)

            textViewName.text = dish.name
            textViewPrice.text = "${dish.price} €"
        }

        return element!!
    }
}


