package com.example.myapplication.itemsRow

import com.example.myapplication.R
import com.example.myapplication.model.ChatMessage
import com.example.myapplication.model.User
import com.example.myapplication.view.MainActivity.Companion.currentUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.latest_message_row.view.*

class LatestMessage(val chatMessage: ChatMessage) : Item<ViewHolder>() {

    var chatPartnerUser: User? = null

    override fun getLayout(): Int {
        return R.layout.latest_message_row
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        val chatPartnerId: String

        if (currentUser?.uid == chatMessage.fromId) {
            viewHolder.itemView.tvLatestMessagesText.text = "You: ${chatMessage.text}"
            chatPartnerId = chatMessage.toId
        } else {
            viewHolder.itemView.tvLatestMessagesText.text = chatMessage.text
            chatPartnerId = chatMessage.fromId
        }

        val partnerRef = FirebaseDatabase.getInstance().getReference("/users/$chatPartnerId")
        partnerRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                chatPartnerUser = p0.getValue(User::class.java)
                viewHolder.itemView.tvLatestMessagesFirstname.text = chatPartnerUser?.firstname
                Picasso.get().load(chatPartnerUser?.profileImage).into(viewHolder.itemView.civLatestMessagesPhoto)
            }

            override fun onCancelled(p0: DatabaseError) {
            }

        })

    }
}