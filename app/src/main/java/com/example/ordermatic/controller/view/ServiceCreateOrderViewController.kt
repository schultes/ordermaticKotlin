package com.example.ordermatic.controller.view

import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.ordermatic.R
import com.example.ordermatic.controller.model.ServiceCreateOrderModelController
import com.example.ordermatic.controller.model.ServiceCreateOrderViewControllerInterface
import com.example.ordermatic.model.Order
import com.example.ordermatic.model.User
import com.example.ordermatic.view.activity.ServiceCreateOrderActivity
import com.example.ordermatic.view.activity.ServiceOrderedDishesOverviewActivity
import com.example.ordermatic.view.helper.setImageFromMipmap
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ServiceCreateOrderViewController(private val activity: ServiceCreateOrderActivity):
    ServiceCreateOrderViewControllerInterface {

    private var modelController: ServiceCreateOrderModelController
    private var user: User

    init {
        modelController = ServiceCreateOrderModelController(this)

        user = activity.intent.getSerializableExtra("EXTRA_USER") as User

        setImageViews()

        val confirmNewOrder: Button = activity.findViewById(R.id.button_newTable_tischAnlegen)
        confirmNewOrder.setOnClickListener(::onCreateNewOrderClicked)
    }

    private fun setImageViews() {
        val imageView: ImageView = activity.findViewById(R.id.imageViewLogo)
        imageView.setImageFromMipmap(R.mipmap.logo_service)
    }


    /******************************************
     * View -> Model
     ******************************************/
    fun onCreateNewOrderClicked(view: View) {
        val tableNumberEditText: EditText = activity.findViewById(R.id.editText_newTable_tischnummer)
        val tableNumber = tableNumberEditText.text.trim().toString()
        val date = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formattedDate = date.format(formatter)
        modelController.addOrder(tableNumber, user.documentId!!, formattedDate)
    }

    /******************************************
     * Model -> View
     ******************************************/
    override fun showOrderDetails(order: Order) {
        val intent = Intent(activity.applicationContext, ServiceOrderedDishesOverviewActivity::class.java)
        intent.putExtra("EXTRA_ORDER", order)
        intent.putExtra("EXTRA_USER", user)
        activity.startActivity(intent)
        activity.finish()
    }

    override fun showError() {
        Toast.makeText(activity.applicationContext, "Der Tisch existiert bereits!", Toast.LENGTH_SHORT).show()
    }
}