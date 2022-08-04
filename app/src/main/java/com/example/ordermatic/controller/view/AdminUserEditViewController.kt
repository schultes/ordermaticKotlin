package com.example.ordermatic.controller.view

import android.view.View
import android.widget.*
import com.example.ordermatic.R
import com.example.ordermatic.controller.model.AdminUserEditModelController
import com.example.ordermatic.controller.model.AdminUserEditViewControllerInterface
import com.example.ordermatic.model.User
import com.example.ordermatic.model.UserRole
import com.example.ordermatic.model.authentication.AuthenticationService
import com.example.ordermatic.view.activity.AdminUserEditActivity
import com.example.ordermatic.view.helper.Validator
import com.example.ordermatic.view.helper.setImageFromMipmap

class AdminUserEditViewController(private val activity: AdminUserEditActivity):
    AdminUserEditViewControllerInterface {

    private var modelController: AdminUserEditModelController

    private var user: User? = null

    private var selectedUserRole: UserRole? = null

    private var editTextPasswordOld: EditText
    private var editTextPasswordNew: EditText
    private var editTextPasswordNewRepeat: EditText

    private var linearLayoutBar: LinearLayout
    private var linearLayoutService: LinearLayout
    private var linearLayoutKitchen: LinearLayout

    private var buttonSave: Button
    private var linearLayoutDelete: LinearLayout

    init {
        modelController = AdminUserEditModelController(this)

        setImageViews()

        user = activity.intent.getSerializableExtra("EXTRA_USER") as User

        editTextPasswordOld = activity.findViewById(R.id.editTextPasswordOld)
        editTextPasswordNew = activity.findViewById(R.id.editTextPasswordNew)
        editTextPasswordNewRepeat = activity.findViewById(R.id.editTextPasswordNewRepeat)

        linearLayoutBar = activity.findViewById(R.id.linearLayoutBar)
        linearLayoutBar.setOnClickListener(::onSelectBarClicked)
        linearLayoutService = activity.findViewById(R.id.linearLayoutService)
        linearLayoutService.setOnClickListener(::onSelectServiceClicked)
        linearLayoutKitchen = activity.findViewById(R.id.linearLayoutKitchen)
        linearLayoutKitchen.setOnClickListener(::onSelectKitchenClicked)

        buttonSave = activity.findViewById(R.id.buttonSave)
        buttonSave.setOnClickListener(::onSaveClicked)

        linearLayoutDelete = activity.findViewById(R.id.linearLayoutDelete)
        linearLayoutDelete.setOnClickListener(::onDeleteClicked)

        user?.let {
            selectedUserRole = it.role

            when (it.role) {
                UserRole.BAR -> linearLayoutBar.setBackgroundResource(R.drawable.button_bar_selected)
                UserRole.SERVICE -> linearLayoutService.setBackgroundResource(R.drawable.button_person_service_selected)
                UserRole.KITCHEN -> linearLayoutKitchen.setBackgroundResource(R.drawable.button_person_kitchen_selected)
            }
        }
    }

    private fun setImageViews() {
        val imageViewLogoAdmin: ImageView = activity.findViewById(R.id.imageViewLogo)
        imageViewLogoAdmin.setImageFromMipmap(R.mipmap.logo_admin)

        val imageViewLogoKitchen: ImageView = activity.findViewById(R.id.kitchen_iconpic)
        imageViewLogoKitchen.setImageFromMipmap(R.mipmap.icon_kitchen)

        val imageViewLogoBar: ImageView = activity.findViewById(R.id.bar_iconpic)
        imageViewLogoBar.setImageFromMipmap(R.mipmap.icon_bar)

        val imageViewLogoService: ImageView = activity.findViewById(R.id.service_iconpic)
        imageViewLogoService.setImageFromMipmap(R.mipmap.icon_service)

        val imageViewDeletePerson: ImageView = activity.findViewById(R.id.deletePerson_iconpic)
        imageViewDeletePerson.setImageFromMipmap(R.mipmap.icon_person_delete)
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
        user?.let { user ->
            val validator = Validator()

            val passwordOld = editTextPasswordOld.text.trim().toString()
            val passwordNew = editTextPasswordNew.text.trim().toString()
            val passwordNewRepeat = editTextPasswordNewRepeat.text.trim().toString()

            validator.checkForPasswordEquality(passwordOld, user.password)
            validator.checkForPasswordComplexity(passwordNew)
            validator.checkForPasswordEquality(passwordNew, passwordNewRepeat)

            if (
                passwordOld.isEmpty()
                && passwordNew.isEmpty()
                && passwordNewRepeat.isEmpty()
                && selectedUserRole != null
            ) {
                AuthenticationService.getCurrentCompanyAccount()?.let { companyAccount ->
                    modelController.editUser(
                        User(
                            companyAccount.id,
                            user.name,
                            user.password,
                            selectedUserRole!!,
                            user.documentId
                        )
                    )
                }
            } else if (
                passwordOld.isEmpty()
                || passwordNew.isEmpty()
                || passwordNewRepeat.isEmpty()
                || selectedUserRole == null
            ) {
                Toast.makeText(activity, "All fields are requiered!", Toast.LENGTH_LONG).show()
            } else if (!validator.isValid()) {
                Toast.makeText(activity, validator.getErrorMessages().joinToString(" / "), Toast.LENGTH_LONG).show()
            } else {
                AuthenticationService.getCurrentCompanyAccount()?.let { companyAccount ->
                    modelController.editUser(
                        User(
                            companyAccount.id,
                            user.name,
                            passwordNew,
                            selectedUserRole!!,
                            user.documentId
                        )
                    )
                }
            }
        }
    }

    private fun onDeleteClicked(v: View) {
        user?.let {
            modelController.deleteUser(it)
        }
    }

    /******************************************
     * Model -> View
     ******************************************/

    override fun onTransactionResult() {
        activity.finish()
    }
}