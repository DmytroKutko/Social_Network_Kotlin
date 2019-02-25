package com.example.myapplication.itemsRow

import com.example.myapplication.R
import com.example.myapplication.model.User
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.user_item_row.view.*

class UserItem(val user: User) : Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.user_item_row
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.tvUserItemFirstname.text = user.firstname
        viewHolder.itemView.tvUserItemLastname.text = user.lastname
        Picasso.get().load(user.profileImage).into(viewHolder.itemView.civUserItemPhoto)
    }
}