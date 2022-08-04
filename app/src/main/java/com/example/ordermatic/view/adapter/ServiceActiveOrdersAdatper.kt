package com.example.ordermatic.view.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.ordermatic.R
import com.example.ordermatic.controller.model.ServiceDashboardModelController
import com.example.ordermatic.model.DishesType
import com.example.ordermatic.model.Order
import com.example.ordermatic.model.User
import com.example.ordermatic.view.activity.ServiceOrderedDishesOverviewActivity

class ServiceActiveOrdersAdatper(context: Context, ressource: Int, orders: List<Order>, user: User) :
    ArrayAdapter<Order>(context, ressource, orders) {

    private var user: User = user

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var modelController = ServiceDashboardModelController(null)

        var view = convertView
        view = LayoutInflater.from(context).inflate(R.layout.list_item_service_table_selection, parent, false)

        val area = view.findViewById<View>(R.id.isRdy_Area)
        val textView: TextView = view.findViewById(R.id.textView_opening_listitem)
        val order = getItem(position)

        if (order != null) {
            modelController.getReadyOrderedDishesOfOrder(order) { readyDishes ->
                if (readyDishes.isNotEmpty()) {
                    textView.text = "Tischnummer ${order.tableNumber} (Bereit)"

                    val types = arrayListOf<DishesType>()
                    for (dish in readyDishes) {
                        modelController.getDishOfOrderedDish(dish) { dishDetails ->
                            types.add(dishDetails.type)
                        }
                    }
                    if (types.contains(DishesType.DRINKS)) {
                        area.setBackgroundResource(R.drawable.button_person_bar)
                    } else if (types.size > 0) {
                        area.setBackgroundResource(R.drawable.button_person_kitchen)
                    }

                } else {
                    textView.text = "Tischnummer ${order.tableNumber}"
                    area.setBackgroundResource(R.color.schriftfarbe)
                }
            }

            view.setOnClickListener {
                context.startActivity(
                    Intent(context, ServiceOrderedDishesOverviewActivity::class.java)
                        .putExtra("EXTRA_ORDER", order)
                        .putExtra("EXTRA_USER", user)

                )
            }
        }
        return view!!
    }
}
