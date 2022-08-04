package com.example.ordermatic.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.ordermatic.R
import com.example.ordermatic.model.Dish
import com.example.ordermatic.view.helper.OnItemClicked
import java.util.*

class ServiceAddDishesToOrderAdatper(val context: Context, val dishes: ArrayList<Dish>, var listener: OnItemClicked) :
    BaseAdapter() {

    override fun getCount(): Int {
        return dishes.size
    }

    override fun getItem(position: Int): Dish {
        return dishes[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var convertView = convertView
        convertView = LayoutInflater.from(context).inflate(R.layout.list_item_service_add_dishes_with_buttons, parent, false)

        val dishesName: TextView = convertView.findViewById(R.id.textView_dish_name)
        dishesName.text = getItem(position).name
        var counter = 0

        val buttonAdd: RelativeLayout = convertView.findViewById(R.id.button_addOneDish)
        val buttonRemove: RelativeLayout = convertView.findViewById(R.id.button_removeOneDish)

        buttonAdd.setOnClickListener {
            counter += 1
            dishesName.text = "${getItem(position).name} ($counter)x"
            listener.onClick(getItem(position))
        }

        buttonRemove.setOnClickListener {
            if (counter == 1) {
                counter -= 1
                dishesName.text = "${getItem(position).name}"
                listener.onRemove(getItem(position))
            } else if (counter > 1) {
                counter -= 1
                dishesName.text = "${getItem(position).name} ($counter)x"
                listener.onRemove(getItem(position))
            }
        }
        return convertView!!
    }
}
