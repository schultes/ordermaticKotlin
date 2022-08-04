package com.example.ordermatic.controller.view

import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.ordermatic.R
import com.example.ordermatic.controller.model.AuthenticationBusinessRegisterModelController
import com.example.ordermatic.controller.model.AuthenticationBusinessRegisterViewControllerInterface
import com.example.ordermatic.view.activity.AuthenticationBusinessRegisterActivity
import com.example.ordermatic.view.activity.GeneralChooseAccountActivity
import com.example.ordermatic.view.helper.Validator
import com.example.ordermatic.view.helper.setImageFromMipmap

class AuthenticationBusinessRegisterViewController(private val activity: AuthenticationBusinessRegisterActivity):
    AuthenticationBusinessRegisterViewControllerInterface {

    private var modelController: AuthenticationBusinessRegisterModelController

    private var editTextCompanyName: EditText
    private var editTextCompanyAddressFirstLine: EditText
    private var editTextCompanyAddressSecondLine: EditText
    private var editTextCompanyPassword: EditText
    private var editTextCompanyPasswordRepeat: EditText

    private var editTextAdminName: EditText
    private var editTextAdminEmail: EditText
    private var editTextAdminPassword: EditText
    private var editTextAdminPasswordRepeat: EditText

    private var buttonRegister: Button

    init {
        modelController = AuthenticationBusinessRegisterModelController(this)

        setImageViews()

        editTextCompanyName = activity.findViewById(R.id.editTextCompanyName)
        editTextCompanyAddressFirstLine = activity.findViewById(R.id.editTextCompanyAddressFirstLine)
        editTextCompanyAddressSecondLine = activity.findViewById(R.id.editTextCompanyAddressSecondLine)
        editTextCompanyPassword = activity.findViewById(R.id.editTextCompanyPassword)
        editTextCompanyPasswordRepeat = activity.findViewById(R.id.editTextCompanyPasswordRepeat)

        editTextAdminName = activity.findViewById(R.id.editTextAdminName)
        editTextAdminEmail = activity.findViewById(R.id.editTextAdminEmail)
        editTextAdminPassword = activity.findViewById(R.id.editTextAdminPassword)
        editTextAdminPasswordRepeat = activity.findViewById(R.id.editTextAdminPasswordRepeat)

        buttonRegister = activity.findViewById(R.id.buttonRegister)
        buttonRegister.setOnClickListener(::onRegisterClicked)
    }

    private fun setImageViews() {
        val imageViewLogoComplete: ImageView = activity.findViewById(R.id.imageView_logo_register_success)
        imageViewLogoComplete.setImageFromMipmap(R.mipmap.logo_complete)
    }


    /******************************************
     * View -> Model
     ******************************************/

    private fun onRegisterClicked(v: View) {

        val validator = Validator()

        val companyName = editTextCompanyName.text.trim().toString()
        val companyAddressFirstLine = editTextCompanyAddressFirstLine.text.trim().toString()
        val companyAddressSecondLine = editTextCompanyAddressSecondLine.text.trim().toString()
        val companyPassword = editTextCompanyPassword.text.trim().toString()
        val companyPasswordRepeat = editTextCompanyPasswordRepeat.text.trim().toString()

        val adminName = editTextAdminName.text.trim().toString()
        val adminEmail = editTextAdminEmail.text.trim().toString()
        val adminPassword = editTextAdminPassword.text.trim().toString()
        val adminPasswordRepeat = editTextAdminPasswordRepeat.text.trim().toString()

        validator.checkForEmailPattern(adminEmail)
        validator.checkForPasswordComplexity(companyPassword)
        validator.checkForPasswordEquality(companyPassword, companyPasswordRepeat)
        validator.checkForPasswordComplexity(adminPassword)
        validator.checkForPasswordEquality(adminPassword, adminPasswordRepeat)

        if (
            companyName.isEmpty()
            || companyAddressFirstLine.isEmpty()
            || companyAddressSecondLine.isEmpty()
            || companyAddressSecondLine.isEmpty()
            || companyPassword.isEmpty()
            || companyPasswordRepeat.isEmpty()
            || adminName.isEmpty()
            || adminEmail.isEmpty()
            || adminPassword.isEmpty()
            || adminPasswordRepeat.isEmpty()
        ) {
            Toast.makeText(activity, "All fields are requiered!", Toast.LENGTH_LONG).show()
        } else if (!validator.isValid()) {
            Toast.makeText(activity, validator.getErrorMessages().joinToString(" / "), Toast.LENGTH_LONG).show()
        } else {
            modelController.onRegisterClicked(
                companyName,
                companyAddressFirstLine,
                companyAddressSecondLine,
                companyPassword,
                adminName,
                adminEmail,
                adminPassword
            )
        }
    }

    /******************************************
     * Model -> View
     ******************************************/

    override fun onRegisterResult(error: String?) {
        if (error.isNullOrEmpty()) {
            activity.startActivity(Intent(activity, GeneralChooseAccountActivity::class.java))
            activity.finish()
        } else {
            Toast.makeText(activity, error, Toast.LENGTH_LONG).show()
        }
    }
}