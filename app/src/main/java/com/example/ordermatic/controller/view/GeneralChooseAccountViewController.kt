package com.example.ordermatic.controller.view

import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.example.ordermatic.R
import com.example.ordermatic.controller.model.GeneralChooseAccountModelController
import com.example.ordermatic.controller.model.GeneralChooseAccountViewControllerInterface
import com.example.ordermatic.model.Company
import com.example.ordermatic.model.User
import com.example.ordermatic.model.authentication.AuthenticationService
import com.example.ordermatic.view.activity.AuthenticationBusinessLoginActivity
import com.example.ordermatic.view.activity.GeneralChooseAccountActivity
import com.example.ordermatic.view.adapter.GeneralChooseAccountAdapter
import com.example.ordermatic.view.helper.setImageFromMipmap

class GeneralChooseAccountViewController(private val activity: GeneralChooseAccountActivity):
    GeneralChooseAccountViewControllerInterface {

    private var modelController: GeneralChooseAccountModelController

    private var textFieldCompanyName: TextView
    private var listViewChooseAccountList: ListView

    private var buttonLogout: Button

    init {
        modelController = GeneralChooseAccountModelController(this)

        setImageViews()

        textFieldCompanyName = activity.findViewById(R.id.textFieldCompanyName)
        listViewChooseAccountList = activity.findViewById(R.id.listViewChooseAccountList)

        buttonLogout = activity.findViewById(R.id.buttonLogout)
        buttonLogout.setOnClickListener(::onLogoutClicked)

        modelController.getCompanyName()
        modelController.getChooseAcccountList()
    }

    private fun setImageViews() {
        val imageViewLogoComplete: ImageView = activity.findViewById(R.id.imageView5)
        imageViewLogoComplete.setImageFromMipmap(R.mipmap.logo_complete)
    }


    /******************************************
     * View -> Model
     ******************************************/

    private fun onLogoutClicked(v: View) {
        AuthenticationService.signOutFromCompanyAccount()
        activity.startActivity(Intent(activity, AuthenticationBusinessLoginActivity::class.java))
        activity.finish()
    }

    /******************************************
     * Model -> View
     ******************************************/

    override fun setCompanyName(company: Company) {
        textFieldCompanyName.text = company.name
    }

    override fun setChooseAccountList(users: List<User>) {
        listViewChooseAccountList.adapter = GeneralChooseAccountAdapter(activity, 0, users)
    }
}