package com.example.ordermatic.controller.view

import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.ordermatic.R
import com.example.ordermatic.controller.model.AuthenticationBusinessLoginModelController
import com.example.ordermatic.controller.model.AuthenticationBusinessLoginViewControllerInterface
import com.example.ordermatic.view.activity.AuthenticationBusinessLoginActivity
import com.example.ordermatic.view.activity.AuthenticationBusinessRegisterActivity
import com.example.ordermatic.view.activity.GeneralChooseAccountActivity
import com.example.ordermatic.view.helper.setImageFromMipmap

class AuthenticationBusinessLoginViewController(private val activity: AuthenticationBusinessLoginActivity):
    AuthenticationBusinessLoginViewControllerInterface {

    private var modelController: AuthenticationBusinessLoginModelController

    private var editTextEmail: EditText
    private var editTextPassword: EditText

    private var buttonLogin: Button
    private var buttonToRegister: Button

    init {
        modelController = AuthenticationBusinessLoginModelController(this)

        setImageViews()

        editTextEmail = activity.findViewById(R.id.editTextEmail)
        editTextPassword = activity.findViewById(R.id.editTextPassword)

        buttonLogin = activity.findViewById(R.id.buttonLogin)
        buttonLogin.setOnClickListener(::onLoginClicked)
        buttonToRegister = activity.findViewById(R.id.buttonToRegister)
        buttonToRegister.setOnClickListener(::onToRegisterClicked)
    }

    private fun setImageViews() {
        val imageViewLogoComplete: ImageView = activity.findViewById(R.id.logo_complete)
        imageViewLogoComplete.setImageFromMipmap(activity, R.mipmap.logo_complete, 222.0f, 180.0f)
    }

    /******************************************
     * View -> Model
     ******************************************/

    private fun onLoginClicked(v: View) {
        val email = editTextEmail.text.trim().toString()
        val password = editTextPassword.text.trim().toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            modelController.onLoginClicked(email, password)
        } else {
            Toast.makeText(activity, "All fields are requiered!", Toast.LENGTH_LONG).show()
        }
    }

    private fun onToRegisterClicked(v: View) {
        activity.startActivity(Intent(activity, AuthenticationBusinessRegisterActivity::class.java))
    }

    /******************************************
     * Model -> View
     ******************************************/

    override fun onLoginResult(error: String?) {
        if (error.isNullOrEmpty()) {
            activity.startActivity(Intent(activity, GeneralChooseAccountActivity::class.java))
            activity.finish()
        } else {
            Toast.makeText(activity, error, Toast.LENGTH_LONG).show()
        }
    }
}