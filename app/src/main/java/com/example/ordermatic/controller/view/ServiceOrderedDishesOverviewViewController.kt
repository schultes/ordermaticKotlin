package com.example.ordermatic.controller.view

import android.content.Intent
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.ordermatic.R
import com.example.ordermatic.controller.model.ServiceOrderedDishesOverviewModelController
import com.example.ordermatic.controller.model.ServiceOrderedDishesOverviewViewControllerInterface
import com.example.ordermatic.model.DishesType
import com.example.ordermatic.model.Order
import com.example.ordermatic.model.OrderedDish
import com.example.ordermatic.model.User
import com.example.ordermatic.view.activity.ServiceAddDishesToOrderActivity
import com.example.ordermatic.view.activity.ServiceAlreadyOrderedDishesActivity
import com.example.ordermatic.view.activity.ServiceOrderedDishesOverviewActivity
import com.example.ordermatic.view.activity.ServicePaymentActivity
import com.example.ordermatic.view.helper.setImageFromMipmap


class ServiceOrderedDishesOverviewViewController(private val activity: ServiceOrderedDishesOverviewActivity):
    ServiceOrderedDishesOverviewViewControllerInterface {

    private var modelController: ServiceOrderedDishesOverviewModelController
    private var order: Order
    private var unsavedOrderedDishes: ArrayList<OrderedDish>
    private var user: User

    init {
        modelController = ServiceOrderedDishesOverviewModelController(this)
        order = activity.intent.getSerializableExtra("EXTRA_ORDER") as Order
        user = activity.intent.getSerializableExtra("EXTRA_USER") as User
        unsavedOrderedDishes = arrayListOf()

        setImageViews()
        setAddDishesClickListener()
        setBasicClickListener()
        setSendOrderClickListener()
        setOrderedDishesListener()
    }

    private fun setImageViews() {
        val imageViewServiceLogo: ImageView = activity.findViewById(R.id.service_logo)
        imageViewServiceLogo.setImageFromMipmap(R.mipmap.logo_service)

        val imageViewIconDrinks: ImageView = activity.findViewById(R.id.getraenke_icon)
        imageViewIconDrinks.setImageFromMipmap(R.mipmap.icon_dishes_drinks)

        val imageViewIconStarters: ImageView = activity.findViewById(R.id.vorspeisen_pic)
        imageViewIconStarters.setImageFromMipmap(R.mipmap.icon_dishes_starters)

        val imageViewIconMainDishes: ImageView = activity.findViewById(R.id.hauptspeisen_pic)
        imageViewIconMainDishes.setImageFromMipmap(R.mipmap.icon_dishes_main_dishes)

        val imageViewIconDesserts: ImageView = activity.findViewById(R.id.desserts_pic)
        imageViewIconDesserts.setImageFromMipmap(R.mipmap.icon_dishes_desserts)

        val imageViewShowOrder: ImageView = activity.findViewById(R.id.ic_showOrder)
        imageViewShowOrder.setImageFromMipmap(R.mipmap.icon_dishes_chosen)

        val imageViewSendOrder: ImageView = activity.findViewById(R.id.bic_sendOrder)
        imageViewSendOrder.setImageFromMipmap(R.mipmap.icon_send)

        val imageViewPayment: ImageView = activity.findViewById(R.id.ic_tablePay)
        imageViewPayment.setImageFromMipmap(R.mipmap.icon_pay)
    }


    /******************************************
     * View -> Model
     ******************************************/
    private fun setSendOrderClickListener() {
        val sendOrder: LinearLayout = activity.findViewById(R.id.Table_SendOrder_Button)
        sendOrder.setOnClickListener {
            if (unsavedOrderedDishes.isNotEmpty())
                modelController.saveOrderedDishes(unsavedOrderedDishes)
            else
                Toast.makeText(activity, "Keine Gerichte ausgewählt!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setOrderedDishesListener() {
        modelController.getReadyOrderedDishesOfOrderListener(order)
    }

    /******************************************
     * Model -> View
     ******************************************/
    fun onActivityResult(data: Intent?) {
        val incomingOrder = data?.getSerializableExtra("EXTRA_ORDER") as ArrayList<OrderedDish>
        val comment = data.getStringExtra("EXTRA_COMMENT")
        for (dish in incomingOrder) {
            if (comment != null && comment.isNotBlank())
                dish.comment = comment
            unsavedOrderedDishes.add(dish)
        }
    }

    override fun onOrderedDishesSave(success: Boolean) {
        if (success) {
            unsavedOrderedDishes = arrayListOf()
            Toast.makeText(activity, "Bestellung gesendet!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(activity, "Fehler", Toast.LENGTH_SHORT).show()
        }
    }

    override fun setReadyOrderChange(orderedDishes: List<OrderedDish>) {
        val textViewAlreadyOrdered: TextView = activity.findViewById(R.id.text_showOrder)
        if (orderedDishes.isNotEmpty()) {
            textViewAlreadyOrdered.text = "Bestellung *"
        } else {
            textViewAlreadyOrdered.text = "Bestellung"
        }
    }

    /******************************************
     * Intents
     ******************************************/
    private fun setAddDishesClickListener() {
        val addDrinks: LinearLayout = activity.findViewById(R.id.edit_getraenke_button)
        val addStarters: LinearLayout = activity.findViewById(R.id.edit_vorspeisen_button)
        val addMainDishes: LinearLayout = activity.findViewById(R.id.edit_hauptspeisen_button)
        val addDesserts: LinearLayout = activity.findViewById(R.id.edit_desserts_button)

        val intent = Intent(activity, ServiceAddDishesToOrderActivity::class.java)
        intent.putExtra("EXTRA_ORDER", order)
        intent.putExtra("EXTRA_UNSAVED_DISHES", unsavedOrderedDishes)

        addDrinks.setOnClickListener {
            intent.putExtra("EXTRA_INTENTION", DishesType.DRINKS)
            activity.startActivityForResult(intent, 204)
        }

        addStarters.setOnClickListener {
            intent.putExtra("EXTRA_INTENTION", DishesType.STARTERS)
            activity.startActivityForResult(intent, 204)
        }

        addMainDishes.setOnClickListener {
            intent.putExtra("EXTRA_INTENTION", DishesType.MAIN_DISHES)
            activity.startActivityForResult(intent, 204)
        }

        addDesserts.setOnClickListener {
            intent.putExtra("EXTRA_INTENTION", DishesType.DESSERTS)
            activity.startActivityForResult(intent, 204)
        }
    }

    private fun setBasicClickListener() {
        val alreadyOrdered: LinearLayout = activity.findViewById(R.id.Table_GetOldOrder_Button)
        val payButton: LinearLayout = activity.findViewById(R.id.Table_Pay_Button)

        alreadyOrdered.setOnClickListener {
            val intent = Intent(activity, ServiceAlreadyOrderedDishesActivity::class.java)
            intent.putExtra("EXTRA_ORDER", order)
            activity.startActivity(intent)
        }

        payButton.setOnClickListener {
            val intent = Intent(activity, ServicePaymentActivity::class.java)
            intent.putExtra("EXTRA_ORDER", order)
            intent.putExtra("EXTRA_USER", user)
            activity.startActivityForResult(intent, 4792506)
        }
    }
}