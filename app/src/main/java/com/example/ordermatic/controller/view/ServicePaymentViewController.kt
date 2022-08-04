package com.example.ordermatic.controller.view

import android.content.Intent
import android.view.View
import android.widget.*
import com.example.ordermatic.R
import com.example.ordermatic.controller.model.ServicePaymentModelController
import com.example.ordermatic.controller.model.ServicePaymentViewControllerInterface
import com.example.ordermatic.model.Order
import com.example.ordermatic.model.OrderDishesStatus
import com.example.ordermatic.model.OrderedDish
import com.example.ordermatic.model.User
import com.example.ordermatic.model.helper.OrderedDishesHelper
import com.example.ordermatic.view.activity.ServicePaymentActivity
import com.example.ordermatic.view.adapter.ServiceDishPayListAdaper
import com.example.ordermatic.view.helper.SetOrderedDishesStatusInterface
import com.example.ordermatic.view.helper.setImageFromMipmap

class ServicePaymentViewController(private val activity: ServicePaymentActivity) :
    SetOrderedDishesStatusInterface, ServicePaymentViewControllerInterface {

    private var modelController: ServicePaymentModelController
    private var order: Order
    private var orderedDishes: List<OrderedDishesHelper>
    private var user: User

    init {
        modelController = ServicePaymentModelController(this)
        order = activity.intent.getSerializableExtra("EXTRA_ORDER") as Order
        orderedDishes = arrayListOf()
        user = activity.intent.getSerializableExtra("EXTRA_USER") as User

        val headline: TextView = activity.findViewById(R.id.Header_Overview_Pay_TextView)
        headline.text = "Tisch ${order.tableNumber}: Check"

        setImageViews()

        val barButton: LinearLayout = activity.findViewById(R.id.button_paymentMethod_bar)
        val ecButton: LinearLayout = activity.findViewById(R.id.button_paymentMethod_ec)
        barButton.setOnClickListener(::payOrder)
        ecButton.setOnClickListener(::payOrder)

        getOrderedDishes()
    }

    private fun setImageViews() {
        val imageViewServiceLogo: ImageView = activity.findViewById(R.id.service_logo)
        imageViewServiceLogo.setImageFromMipmap(R.mipmap.logo_service)

        val imageViewPayCard: ImageView = activity.findViewById(R.id.ic_pay_card)
        imageViewPayCard.setImageFromMipmap(R.mipmap.icon_pay_card)

        val imageViewPayCash: ImageView = activity.findViewById(R.id.ic_pay_cash)
        imageViewPayCash.setImageFromMipmap(R.mipmap.icon_pay_cash)
    }


    /******************************************
     * View -> Model
     ******************************************/
    private fun payOrder(view: View) {
        modelController.archiveOrder(order)
    }

    private fun getOrderedDishes() {
        modelController.getOrderedDishesOfOrder(order)
    }

    override fun setOrderedDishesStatus(orderedDish: OrderedDish, dishesStatus: OrderDishesStatus) {
        modelController.setOrderedDishesStatus(orderedDish, dishesStatus)
    }

    /******************************************
     * Model -> View
     ******************************************/
    override fun setOrderedDishesOfOrder(orderedDishesList: List<OrderedDishesHelper>) {
        this.orderedDishes = orderedDishesList
        val listView: ListView = activity.findViewById(R.id.listView_allOrderedDishes)
        val textViewWholeBill: TextView = activity.findViewById(R.id.textView_wholeBill)
        var bill = 0.0
        if (orderedDishes.isNotEmpty()) {
            bill = orderedDishes.map { d -> d.dish.price.toDouble() }.reduce { r1, r2 ->
                r1 + r2
            }
        }
        textViewWholeBill.text = "Gesamt: ${String.format("%.2f", bill)} €"
        listView.adapter = ServiceDishPayListAdaper(activity.applicationContext, orderedDishes, this)
    }

    override fun setPaymentSuccess(success: Boolean) {
        if (success) {
            activity.setResult(4792506, Intent().putExtra("SHOULD_FINISH", true))
            activity.finish()
        } else {
            Toast.makeText(activity, "Ein Fehler ist aufgetreten!", Toast.LENGTH_SHORT).show()
        }
    }


}