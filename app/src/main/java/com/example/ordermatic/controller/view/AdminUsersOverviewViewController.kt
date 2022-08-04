package com.example.ordermatic.controller.view

import android.content.Intent
import android.view.View
import android.widget.GridView
import android.widget.ImageView
import com.example.ordermatic.R
import com.example.ordermatic.controller.model.AdminUsersOverviewModelController
import com.example.ordermatic.controller.model.AdminUsersOverviewViewControllerInterface
import com.example.ordermatic.model.User
import com.example.ordermatic.view.activity.AdminUserCreateActivity
import com.example.ordermatic.view.activity.AdminUsersOverviewActivity
import com.example.ordermatic.view.adapter.AdminUsersOverviewAdapter
import com.example.ordermatic.view.helper.setImageFromMipmap
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AdminUsersOverviewViewController(private val activity: AdminUsersOverviewActivity):
    AdminUsersOverviewViewControllerInterface {

    private var modelController: AdminUsersOverviewModelController

    private var gridViewUsers: GridView
    private var floatingActionButtonCreateDishes: FloatingActionButton

    init {
        modelController = AdminUsersOverviewModelController(this)

        setImageViews()

        gridViewUsers = activity.findViewById(R.id.gridViewUsers)
        floatingActionButtonCreateDishes = activity.findViewById(R.id.floatingActionButtonCreateUser)
        floatingActionButtonCreateDishes.setOnClickListener(::onCreateDishesClicked)

        modelController.getUsers()
    }

    private fun setImageViews() {
        val imageViewLogoAdmin: ImageView = activity.findViewById(R.id.imageViewLogo)
        imageViewLogoAdmin.setImageFromMipmap(R.mipmap.logo_admin)
    }


    /******************************************
     * View -> Model
     ******************************************/

    private fun onCreateDishesClicked(v: View) {
        activity.startActivity(
            Intent(activity, AdminUserCreateActivity::class.java)
        )
    }

    /******************************************
     * Model -> View
     ******************************************/

    override fun setUsers(users: List<User>) {
        gridViewUsers.adapter = AdminUsersOverviewAdapter(activity, 0, users)
    }
}