package com.example.ordermatic.view.adapter


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import com.example.ordermatic.R
import com.example.ordermatic.model.User
import com.example.ordermatic.model.UserRole
import com.example.ordermatic.view.activity.AdminUserEditActivity


class AdminUsersOverviewAdapter(context: Context, resource: Int, users: List<User>) :
    ArrayAdapter<User>(context, resource, users) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var element = convertView
        val inflater = LayoutInflater.from(context)
        val user = getItem(position)

        if (user != null) {
            element = inflater.inflate(R.layout.list_item_admin_users, null)

            val buttonUser = element.findViewById<Button>(R.id.buttonUser)
            buttonUser.text = user.name
            buttonUser.setOnClickListener {
                context.startActivity(
                    Intent(context, AdminUserEditActivity::class.java).putExtra("EXTRA_USER", user)
                )
            }

            when (user.role) {
                UserRole.ADMIN -> buttonUser.setBackgroundResource(R.drawable.button_person_admin_property)
                UserRole.SERVICE -> buttonUser.setBackgroundResource(R.drawable.button_person_service_property)
                UserRole.KITCHEN -> buttonUser.setBackgroundResource(R.drawable.button_person_kitchen_property)
                UserRole.BAR -> buttonUser.setBackgroundResource(R.drawable.button_person_bar_property)
            }
        }

        return element!!
    }
}