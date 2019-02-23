package com.example.myapplication.adapter

import com.example.myapplication.R
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.chat_item_row_to.view.*

class ChatItemTo(val text : String) : Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.chat_item_row_to
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.tvChatItemMessageTo.text = text
    }
}