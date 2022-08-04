package com.example.ordermatic.controller.view

import android.content.Intent
import android.view.View
import android.widget.*
import com.example.ordermatic.R
import com.example.ordermatic.model.User
import com.example.ordermatic.model.UserRole
import com.example.ordermatic.view.activity.AdminDashboardActivity
import com.example.ordermatic.view.activity.GeneralUserLoginActivity
import com.example.ordermatic.view.activity.KitchenBarDashboardActivity
import com.example.ordermatic.view.activity.ServiceDashboardActivity
import com.example.ordermatic.view.helper.setImageFromMipmap

class GeneralUserLoginViewController(private val activity: GeneralUserLoginActivity) {

    private var user: User

    private var imageViewLogo: ImageView
    private var textViewRole: TextView
    private var editTextPassword: EditText
    private var buttonLogin: Button
    private var textViewLogin: TextView

    init {
        user = activity.intent.getSerializableExtra("EXTRA_USER") as User

        imageViewLogo = activity.findViewById(R.id.imageViewLogo)
        textViewRole = activity.findViewById(R.id.textViewRole)
        editTextPassword = activity.findViewById(R.id.editTextPassword)

        setImageViews()

        buttonLogin = activity.findViewById(R.id.buttonLogin)
        buttonLogin.setOnClickListener(::onLoginClicked)

        textViewLogin = activity.findViewById(R.id.textViewLogin)

        //Setup
        when (user.role) {
            UserRole.ADMIN -> {
                imageViewLogo.setImageFromMipmap(R.mipmap.icon_person_edit)
            }
            UserRole.SERVICE -> {
                imageViewLogo.setImageFromMipmap(R.mipmap.icon_service)
            }
            UserRole.KITCHEN -> {
                imageViewLogo.setImageFromMipmap(R.mipmap.icon_kitchen)
            }
            UserRole.BAR -> {
                imageViewLogo.setImageFromMipmap(R.mipmap.icon_bar)
            }
        }

        textViewRole.text = "${user.role.rawValue.capitalize()}-Login"
        textViewLogin.text = "Hallo ${user.name}, gib bitte dein Passwort ein!"
    }

    private fun setImageViews() {
        val imageView: ImageView = activity.findViewById(R.id.imageViewLogo)
        imageView.setImageFromMipmap(R.mipmap.logo_admin)
    }


    private fun onLoginClicked(v: View) {
        val password = editTextPassword.text.trim().toString()

        if (password.isEmpty() || password != user.password) {
            Toast.makeText(activity, "Falsches Passwort!", Toast.LENGTH_SHORT).show()
        } else {
            if (user.role == UserRole.ADMIN) {
                activity.startActivity(
                    Intent(activity, AdminDashboardActivity::class.java)
                )
                activity.finish()
            } else if (user.role == UserRole.KITCHEN || user.role == UserRole.BAR) {
                activity.startActivity(
                    Intent(activity, KitchenBarDashboardActivity::class.java)
                        .putExtra("EXTRA_USER", user)
                )
                activity.finish()
            } else if (user.role == UserRole.SERVICE) {
                activity.startActivity(
                    Intent(activity, ServiceDashboardActivity::class.java)
                        .putExtra("EXTRA_USER", user)
                )
                activity.finish()
            }
        }
    }
}