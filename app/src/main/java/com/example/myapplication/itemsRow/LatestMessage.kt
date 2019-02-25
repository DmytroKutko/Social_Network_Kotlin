package com.example.myapplication.itemsRow

import com.example.myapplication.R
import com.example.myapplication.model.ChatMessage
import com.example.myapplication.view.MainActivity.Companion.currentUser
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.latest_message_row.view.*

class LatestMessage(val chatMessage: ChatMessage) : Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.latest_message_row
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        if (currentUser?.uid == chatMessage.fromId) {
            viewHolder.itemView.tvLatestMessagesText.text = "You: ${chatMessage.text}"
        } else{
            viewHolder.itemView.tvLatestMessagesText.text = chatMessage.text
        }
    }
}