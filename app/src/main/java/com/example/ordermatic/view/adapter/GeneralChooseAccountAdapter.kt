package com.example.ordermatic.view.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.ordermatic.R
import com.example.ordermatic.model.User
import com.example.ordermatic.model.UserRole
import com.example.ordermatic.view.activity.GeneralUserLoginActivity
import com.example.ordermatic.view.helper.setImageFromMipmap

class GeneralChooseAccountAdapter(context: Context, ressource: Int, users: List<User>) :
    ArrayAdapter<User>(context, ressource, users) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var element = convertView
        val inflater = LayoutInflater.from(context)
        val user = getItem(position)

        if (user != null) {

            element = when (user.role) {
                UserRole.ADMIN -> inflater.inflate(R.layout.list_item_general_choose_account_admin, null)
                UserRole.SERVICE -> inflater.inflate(R.layout.list_item_general_choose_account_service, null)
                UserRole.KITCHEN -> inflater.inflate(R.layout.list_item_general_choose_account_kitchen, null)
                UserRole.BAR -> inflater.inflate(R.layout.list_item_general_choose_account_bar, null)
            }

            if (element != null) {

                setImageView(element, user)

                val textViewAccountName = element.findViewById<TextView>(R.id.textViewAccountName)
                val buttonAccount = element.findViewById<ConstraintLayout>(R.id.constraintLayoutAccount)

                textViewAccountName.text = user.name
                buttonAccount.setOnClickListener {
                    context.startActivity(
                        Intent(this.context, GeneralUserLoginActivity::class.java)
                            .putExtra("EXTRA_USER", user)
                    )
                }
            }
        }

        return element!!
    }

    private fun setImageView(element: View, user: User) {
        when (user.role) {
            UserRole.ADMIN -> {
                val imageView = element.findViewById<ImageView>(R.id.edit_person)
                imageView.setImageFromMipmap(R.mipmap.icon_person_edit)
            }
            UserRole.SERVICE -> {
                val imageView = element.findViewById<ImageView>(R.id.list_ac_service_pic)
                imageView.setImageFromMipmap(R.mipmap.icon_service)
            }
            UserRole.KITCHEN -> {
                val imageView = element.findViewById<ImageView>(R.id.list_ac_kitchen_pic)
                imageView.setImageFromMipmap(R.mipmap.icon_kitchen)
            }
            UserRole.BAR -> {
                val imageView = element.findViewById<ImageView>(R.id.list_ac_bar_pic)
                imageView.setImageFromMipmap(R.mipmap.icon_bar)
            }
        }
    }
}
