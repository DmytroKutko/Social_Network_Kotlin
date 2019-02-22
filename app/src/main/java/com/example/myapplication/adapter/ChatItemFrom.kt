package com.example.myapplication.adapter

import com.example.myapplication.R
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder

class ChatItemFrom : Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.chat_item_row_from
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {

    }
}