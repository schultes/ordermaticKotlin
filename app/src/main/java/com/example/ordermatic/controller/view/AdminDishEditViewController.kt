package com.example.ordermatic.controller.view

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import com.example.ordermatic.R
import com.example.ordermatic.controller.model.AdminDishEditModelController
import com.example.ordermatic.controller.model.AdminDishEditViewControllerInterface
import com.example.ordermatic.model.Dish
import com.example.ordermatic.model.DishesType
import com.example.ordermatic.model.authentication.AuthenticationService
import com.example.ordermatic.view.activity.AdminDishEditActivity
import com.example.ordermatic.view.helper.setImageFromMipmap

class AdminDishEditViewController(private val activity: AdminDishEditActivity):
    AdminDishEditViewControllerInterface {

    private var modelController: AdminDishEditModelController

    private var selectedDishesType: DishesType? = null
    private var dish: Dish? = null

    private var textViewTitle: TextView
    private var textViewDescription: TextView
    private var textViewPrice: TextView

    private var buttonSave: Button
    private var buttonDelete: Button

    init {
        modelController = AdminDishEditModelController(this)
        setImageViews()

        if (activity.intent.hasExtra("EXTRA_SELECTED_DISHES_TYPE")) {
            selectedDishesType = activity.intent.getSerializableExtra("EXTRA_SELECTED_DISHES_TYPE") as DishesType
        }

        if (activity.intent.hasExtra("EXTRA_DISH")) {
            dish = activity.intent.getSerializableExtra("EXTRA_DISH") as Dish
        }

        textViewTitle = activity.findViewById(R.id.textViewTitle)
        textViewDescription = activity.findViewById(R.id.textViewDescription)
        textViewPrice = activity.findViewById(R.id.textViewPrice)

        buttonSave = activity.findViewById(R.id.buttonSave)
        buttonSave.setOnClickListener(::onSaveClicked)
        buttonDelete = activity.findViewById(R.id.buttonDelete)
        buttonDelete.text = "Löschen"
        buttonDelete.isVisible = false
        buttonDelete.setOnClickListener(::onDeleteClicked)


        dish?.let { dish ->
            buttonDelete.isVisible = true
            textViewTitle.text = dish.name
            textViewDescription.text = dish.description
            textViewPrice.text = dish.price
        }
    }

    private fun setImageViews() {
        val imageViewLogoAdmin: ImageView = activity.findViewById(R.id.imageViewLogo)
        imageViewLogoAdmin.setImageFromMipmap(R.mipmap.logo_admin)
    }

    /******************************************
     * View -> Model
     ******************************************/

    private fun onSaveClicked(v: View) {

        val title = textViewTitle.text.trim().toString()
        val description = textViewDescription.text.trim().toString()
        val price = textViewPrice.text.trim().toString()

        if (selectedDishesType != null && dish == null) {
            AuthenticationService.getCurrentCompanyAccount()?.let { companyAccount ->
                modelController.addDish(
                    Dish(
                        companyAccount.id,
                        title,
                        description,
                        price,
                        selectedDishesType!!
                    )
                )
            }
        } else {
            dish?.let { dish ->
                modelController.editDish(
                    Dish(
                        dish.companyReference,
                        title,
                        description,
                        price,
                        dish.type,
                        dish.documentId
                    )
                )
            }
        }
    }

    private fun onDeleteClicked(v: View) {
        dish?.let { dish ->
            modelController.deleteDish(dish)
        }
    }

    /******************************************
     * Model -> View
     ******************************************/

    override fun onTransactionResult() {
        activity.finish()
    }
}