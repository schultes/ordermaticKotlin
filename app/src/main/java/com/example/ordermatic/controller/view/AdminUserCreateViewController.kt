package com.example.ordermatic.controller.view

import android.view.View
import android.widget.*
import com.example.ordermatic.R
import com.example.ordermatic.controller.model.AdminUserCreateModelController
import com.example.ordermatic.controller.model.AdminUserCreateViewControllerInterface
import com.example.ordermatic.model.User
import com.example.ordermatic.model.UserRole
import com.example.ordermatic.model.authentication.AuthenticationService
import com.example.ordermatic.view.activity.AdminUserCreateActivity
import com.example.ordermatic.view.helper.Validator
import com.example.ordermatic.view.helper.setImageFromMipmap

class AdminUserCreateViewController(private val activity: AdminUserCreateActivity):
    AdminUserCreateViewControllerInterface {

    private var modelController: AdminUserCreateModelController

    private var selectedUserRole: UserRole? = null

    private var editTextName: EditText
    private var editTextPassword: EditText
    private var editTextPasswordRepeat: EditText

    private var linearLayoutBar: LinearLayout
    private var linearLayoutService: LinearLayout
    private var linearLayoutKitchen: LinearLayout

    private var buttonSave: Button

    init {
        modelController = AdminUserCreateModelController(this)

        setImageViews()

        editTextName = activity.findViewById(R.id.editTextName)
        editTextPassword = activity.findViewById(R.id.editTextPassword)
        editTextPasswordRepeat = activity.findViewById(R.id.editTextPasswordRepeat)

        linearLayoutBar = activity.findViewById(R.id.linearLayoutBar)
        linearLayoutBar.setOnClickListener(::onSelectBarClicked)
        linearLayoutService = activity.findViewById(R.id.linearLayoutService)
        linearLayoutService.setOnClickListener(::onSelectServiceClicked)
        linearLayoutKitchen = activity.findViewById(R.id.linearLayoutKitchen)
        linearLayoutKitchen.setOnClickListener(::onSelectKitchenClicked)

        buttonSave = activity.findViewById(R.id.buttonSave)
        buttonSave.setOnClickListener(::onSaveClicked)
    }

    private fun setImageViews() {
        val imageViewLogoAdmin: ImageView = activity.findViewById(R.id.imageViewLogo)
        imageViewLogoAdmin.setImageFromMipmap(R.mipmap.logo_admin)

        val imageViewKitchenIcon: ImageView = activity.findViewById(R.id.kitchen_iconpic)
        imageViewKitchenIcon.setImageFromMipmap(R.mipmap.icon_kitchen)

        val imageViewServiceIcon: ImageView = activity.findViewById(R.id.service_iconpic)
        imageViewServiceIcon.setImageFromMipmap(R.mipmap.icon_service)

        val imageViewLogoBar: ImageView = activity.findViewById(R.id.bar_iconpic)
        imageViewLogoBar.setImageFromMipmap(R.mipmap.icon_bar)
    }


    /******************************************
     * View -> Model
     ******************************************/

    private fun onSelectBarClicked(v: View) {
        selectedUserRole = UserRole.BAR
        linearLayoutBar.setBackgroundResource(R.drawable.button_bar_selected)
        linearLayoutService.setBackgroundResource(R.drawable.button_person_service)
        linearLayoutKitchen.setBackgroundResource(R.drawable.button_person_kitchen)
    }

    private fun onSelectServiceClicked(v: View) {
        selectedUserRole = UserRole.SERVICE
        linearLayoutBar.setBackgroundResource(R.drawable.button_person_bar)
        linearLayoutService.setBackgroundResource(R.drawable.button_person_service_selected)
        linearLayoutKitchen.setBackgroundResource(R.drawable.button_person_kitchen)
    }

    private fun onSelectKitchenClicked(v: View) {
        selectedUserRole = UserRole.KITCHEN
        linearLayoutBar.setBackgroundResource(R.drawable.button_person_bar)
        linearLayoutService.setBackgroundResource(R.drawable.button_person_service)
        linearLayoutKitchen.setBackgroundResource(R.drawable.button_person_kitchen_selected)
    }

    private fun onSaveClicked(v: View) {

        val validator = Validator()

        val name = editTextName.text.trim().toString()
        val password = editTextPassword.text.trim().toString()
        val passwordRepeat = editTextPasswordRepeat.text.trim().toString()

        validator.checkForPasswordComplexity(password)
        validator.checkForPasswordEquality(password, passwordRepeat)

        if (
            name.isEmpty()
            || password.isEmpty()
            || passwordRepeat.isEmpty()
            || selectedUserRole == null
        ) {
            Toast.makeText(activity, "All fields are requiered!", Toast.LENGTH_LONG).show()
        } else if (!validator.isValid()) {
            Toast.makeText(activity, validator.getErrorMessages().joinToString(" / "), Toast.LENGTH_LONG).show()
        } else {
            AuthenticationService.getCurrentCompanyAccount()?.let { companyAccount ->
                modelController.addUser(
                    User(
                        companyAccount.id,
                        name,
                        password,
                        selectedUserRole!!
                    )
                )
            }
        }
    }

    /******************************************
     * Model -> View
     ******************************************/

    override fun onTransactionResult() {
        activity.finish()
    }
}