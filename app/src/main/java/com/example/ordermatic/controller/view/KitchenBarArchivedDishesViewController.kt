package com.example.ordermatic.controller.view

import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.example.ordermatic.R
import com.example.ordermatic.controller.model.KitchenBarArchivedDishesModelController
import com.example.ordermatic.controller.model.KitchenBarArchivedDishesViewControllerInterface
import com.example.ordermatic.model.Order
import com.example.ordermatic.model.User
import com.example.ordermatic.model.UserRole
import com.example.ordermatic.model.helper.OrderedDishesHelper
import com.example.ordermatic.view.activity.KitchenBarArchivedDishesActivity
import com.example.ordermatic.view.adapter.KitchenBarArchivedDishesAdapter
import com.example.ordermatic.view.helper.setImageFromMipmap

class KitchenBarArchivedDishesViewController(private val activity: KitchenBarArchivedDishesActivity):
    KitchenBarArchivedDishesViewControllerInterface {
    private var modelController: KitchenBarArchivedDishesModelController
    var user: User
    var order: Order
    var orderedDishesWithDishes: List<OrderedDishesHelper>
    private var changedOrderedDishes: List<OrderedDishesHelper>

    init {
        modelController = KitchenBarArchivedDishesModelController(this)
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

        val textViewTopBar: TextView = activity.findViewById(R.id.kb_olddishes_header)
        textViewTopBar.text = "Bestellungsdetails"

        getArchivedDishesOfOrder()
    }

    /******************************************
     * View -> Model
     ******************************************/
    fun getArchivedDishesOfOrder() {
        modelController.getArchivedDishesOfOrder(order, user)
    }

    /******************************************
     * Model -> View
     ******************************************/
    override fun setArchivedDishesOfOrder(list: List<OrderedDishesHelper>) {
        this.orderedDishesWithDishes = list
        val dishListView: ListView = activity.findViewById(R.id.olddishes_list)
        dishListView.adapter = KitchenBarArchivedDishesAdapter(activity.applicationContext, 0, orderedDishesWithDishes)
    }
}