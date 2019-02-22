package com.example.myapplication.adapter

import com.example.myapplication.R
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder

class ChatItemTo : Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.chat_item_row_to
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {

    }
}