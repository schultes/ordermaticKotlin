package com.example.ordermatic.controller.view

import android.app.Activity.RESULT_CANCELED
import android.content.Intent
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.example.ordermatic.R
import com.example.ordermatic.controller.model.ServiceAddDishesToOrderModelController
import com.example.ordermatic.controller.model.ServiceAddDishesToOrderViewControllerInterface
import com.example.ordermatic.model.*
import com.example.ordermatic.view.activity.ServiceAddDishesToOrderActivity
import com.example.ordermatic.view.activity.ServiceOrderedDishesOverviewActivity
import com.example.ordermatic.view.adapter.ServiceAddDishesToOrderAdatper
import com.example.ordermatic.view.helper.setImageFromMipmap

class ServiceAddDishesToOrderViewController(private val activity: ServiceAddDishesToOrderActivity):
    ServiceAddDishesToOrderViewControllerInterface {

    private var modelController: ServiceAddDishesToOrderModelController
    private var dishesList = ArrayList<Dish>()
    private var usavedOrderedDishesList = ArrayList<OrderedDish>()
    private var order: Order

    init {
        modelController = ServiceAddDishesToOrderModelController(this)
        order = activity.intent.getSerializableExtra("EXTRA_ORDER") as Order

        setImageViews()

        setButtonSendValuesClickListener()
        getDishesListView()
    }

    private fun setImageViews() {
        val imageView: ImageView = activity.findViewById(R.id.imageViewLogo)
        imageView.setImageFromMipmap(R.mipmap.logo_service)
    }


    private fun setButtonSendValuesClickListener() {
        val buttonSendValues: Button = activity.findViewById(R.id.button_addToOrder)
        val textViewComment: TextView = activity.findViewById(R.id.editText_multiline_comment)

        for (dish in usavedOrderedDishesList) {
            dish.comment = textViewComment.text.toString()
        }

        buttonSendValues.setOnClickListener {
            val intent = Intent(activity, ServiceOrderedDishesOverviewActivity::class.java)
            if (usavedOrderedDishesList.isNotEmpty()) {
                intent.putExtra("EXTRA_ORDER", usavedOrderedDishesList)
                intent.putExtra("EXTRA_COMMENT", textViewComment.text.toString())
                activity.setResult(204, intent)
            } else {
                activity.setResult(RESULT_CANCELED, intent)
            }
            activity.finish()
        }
    }

    fun addDishToUnsavedOrder(value: Dish) {
        val temp = OrderedDish(
            order.companyReference,
            order.userReference,
            order.documentId!!,
            value.documentId!!,
            OrderDishesStatus.UNDONE,
            ""
        )
        usavedOrderedDishesList.add(temp)
    }

    fun removeDishFromUnsavedOrder(value: Dish) {
        for (dish in usavedOrderedDishesList) {
            if (dish.dishReference == value.documentId) {
                usavedOrderedDishesList.remove(dish)
                return
            }
        }
    }

    /******************************************
     * View -> Model
     ******************************************/
    private fun getDishesListView() {
        val dishesType: DishesType = activity.intent.getSerializableExtra("EXTRA_INTENTION") as DishesType
        modelController.getDishesListView(dishesType)
    }

    /******************************************
     * Model -> View
     ******************************************/
    override fun setDishesListView(dishes: List<Dish>) {
        val listView: ListView = activity.findViewById(R.id.listView_forDishes)
        for (dish in dishes) {
            dishesList.add(dish)
        }
        listView.adapter = ServiceAddDishesToOrderAdatper(activity.applicationContext, dishesList, activity)
    }
}