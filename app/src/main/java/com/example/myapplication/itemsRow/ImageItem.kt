package com.example.myapplication.itemsRow

import android.util.Log
import com.example.myapplication.R
import com.example.myapplication.model.Image
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.gallery_item.view.*

class ImageItem(val image: Image) : Item<ViewHolder>() {

    override fun getLayout(): Int {
        return R.layout.gallery_item
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        Picasso.get().load(image.url).into(viewHolder.itemView.ivGalleryItem)
    }
}