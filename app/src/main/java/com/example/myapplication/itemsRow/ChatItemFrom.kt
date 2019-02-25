package com.example.myapplication.itemsRow

import com.example.myapplication.R
import com.example.myapplication.model.ChatMessage
import com.example.myapplication.model.User
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.chat_item_row_from.view.*

class ChatItemFrom(val chatMessage: ChatMessage, val user: User) : Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.chat_item_row_from
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.tvChatItemMessageFrom.text = chatMessage.text
        Picasso.get().load(user.profileImage).into(viewHolder.itemView.civChatItemPhotoFrom)
    }
}