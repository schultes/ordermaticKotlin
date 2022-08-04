package com.example.ordermatic.controller.view

import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.example.ordermatic.R
import com.example.ordermatic.controller.model.ServiceAlreadyOrderedDishesModelController
import com.example.ordermatic.controller.model.ServiceAlreadyOrderedDishesViewControllerInterface
import com.example.ordermatic.model.Order
import com.example.ordermatic.model.OrderDishesStatus
import com.example.ordermatic.model.OrderedDish
import com.example.ordermatic.model.helper.OrderedDishesHelper
import com.example.ordermatic.view.activity.ServiceAlreadyOrderedDishesActivity
import com.example.ordermatic.view.adapter.ServiceDishPayListAdaper
import com.example.ordermatic.view.helper.SetOrderedDishesStatusInterface
import com.example.ordermatic.view.helper.setImageFromMipmap


class ServiceAlreadyOrderedDishesViewController(private val activity: ServiceAlreadyOrderedDishesActivity) :
    SetOrderedDishesStatusInterface, ServiceAlreadyOrderedDishesViewControllerInterface {

    private var modelController: ServiceAlreadyOrderedDishesModelController
    private var currentOrder: Order
    private var orderedDishes: List<OrderedDishesHelper>

    init {
        modelController = ServiceAlreadyOrderedDishesModelController(this)
        currentOrder = activity.intent.getSerializableExtra("EXTRA_ORDER") as Order
        orderedDishes = arrayListOf()

        setImageViews()
        getOrderedDishes()
    }

    private fun setImageViews() {
        val imageView: ImageView = activity.findViewById(R.id.imageViewLogo)
        imageView.setImageFromMipmap(R.mipmap.logo_service)
    }

    /******************************************
     * View -> Model
     ******************************************/
    private fun getOrderedDishes() {
        modelController.getOrderedDishesOfActiveOrder(currentOrder)
    }

    override fun setOrderedDishesStatus(orderedDish: OrderedDish, dishesStatus: OrderDishesStatus) {
        modelController.setOrderedDishesStatus(orderedDish, dishesStatus)
    }

    /******************************************
     * Model -> View
     ******************************************/
    override fun setOrderedDishes(orderedDishesList: List<OrderedDishesHelper>) {
        this.orderedDishes = orderedDishesList
        val listView: ListView = activity.findViewById(R.id.listView_alreadyOrdered)
        val textViewWholeBill: TextView = activity.findViewById(R.id.textView_alreadyOrdered_wholeBill)
        var bill = 0.0
        if (orderedDishes.isNotEmpty()) {
            bill = orderedDishes.map { d -> d.dish.price.toDouble() }.reduce { r1, r2 ->
                r1 + r2
            }
        }
        val billText = "Gesamt: ${String.format("%.2f", bill)} €"
        textViewWholeBill.text = billText
        listView.adapter = ServiceDishPayListAdaper(activity.applicationContext, orderedDishes, this)
    }
}