package com.example.ordermatic.controller.view

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.example.ordermatic.R
import com.example.ordermatic.controller.model.KitchenBarActiveDishesModelController
import com.example.ordermatic.controller.model.KitchenBarActiveDishesViewControllerInterface
import com.example.ordermatic.model.Order
import com.example.ordermatic.model.OrderDishesStatus
import com.example.ordermatic.model.User
import com.example.ordermatic.model.UserRole
import com.example.ordermatic.model.helper.OrderedDishesHelper
import com.example.ordermatic.view.activity.KitchenBarActiveDishesActivity
import com.example.ordermatic.view.adapter.KitchenBarActiveDishesAdapter
import com.example.ordermatic.view.helper.setImageFromMipmap

class KitchenBarActiveDishesViewController(private val activity: KitchenBarActiveDishesActivity):
    KitchenBarActiveDishesViewControllerInterface {

    private var modelController: KitchenBarActiveDishesModelController
    var user: User
    var order: Order
    var orderedDishesWithDishes: List<OrderedDishesHelper>
    private var changedOrderedDishes: List<OrderedDishesHelper>

    init {
        modelController = KitchenBarActiveDishesModelController(this)
        user = activity.intent.getSerializableExtra("EXTRA_USER") as User
        order = activity.intent.getSerializableExtra("EXTRA_ORDER") as Order
        orderedDishesWithDishes = arrayListOf()
        changedOrderedDishes = arrayListOf()

        val imageViewLogoIcon: ImageView = activity.findViewById(R.id.imageViewLogo)
        if (user.role == UserRole.KITCHEN) {
            imageViewLogoIcon.setImageFromMipmap(R.mipmap.logo_kitchen)
        } else { // bar
            imageViewLogoIcon.setImageFromMipmap(R.mipmap.logo_bar)
        }

        val textViewTopBar: TextView = activity.findViewById(R.id.kb_activedishes_header)
        textViewTopBar.text = "Bestellungsdetails"

        getActiveDishes()
        val sendButton: Button = activity.findViewById(R.id.set_dishes_btn)
        sendButton.setOnClickListener(::sendStatusOfOrders)
    }

    fun onCheckboxClicked(orderedDishReference: String, isChecked: Boolean) {
        val checkedOrderedDish = orderedDishesWithDishes.find { obj -> obj.orderedDish.documentId!! == orderedDishReference }
        if (isChecked) {
            (changedOrderedDishes as ArrayList).add(checkedOrderedDish!!)
        } else {
            (changedOrderedDishes as ArrayList).remove(checkedOrderedDish!!)
        }
    }

    /******************************************
     * View -> Model
     ******************************************/
    fun getActiveDishes() {
        modelController.getActiveDishesOfOrder(order, user)
    }

    private fun sendStatusOfOrders(view: View) {
        if (changedOrderedDishes.isNotEmpty()) {
            val orderIds = changedOrderedDishes.map { obj -> obj.orderedDish.documentId!! }
            modelController.setStatusOfOrderedDishes(orderIds, order.documentId!!)
            changedOrderedDishes = arrayListOf()
        }
    }

    /******************************************
     * Model -> View
     ******************************************/
    override fun setActiveDishes(list: List<OrderedDishesHelper>) {
        this.orderedDishesWithDishes = list
        val comment: TextView = activity.findViewById(R.id.kb_comments)
        comment.text = ""
        if (orderedDishesWithDishes.isNotEmpty()) {
            for (d in orderedDishesWithDishes) {
                if (d.orderedDish.comment.isNotBlank())
                    comment.text = d.orderedDish.comment
            }
        }
        val dishListView: ListView = activity.findViewById(R.id.listViewDishes)
        dishListView.adapter = KitchenBarActiveDishesAdapter(activity.applicationContext, 0, orderedDishesWithDishes, this)
    }
}