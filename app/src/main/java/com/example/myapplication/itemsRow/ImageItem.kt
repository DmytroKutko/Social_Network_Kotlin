package com.example.myapplication.itemsRow

import com.example.myapplication.R
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder

class ImageItem: Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.gallery_item
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {

    }
}