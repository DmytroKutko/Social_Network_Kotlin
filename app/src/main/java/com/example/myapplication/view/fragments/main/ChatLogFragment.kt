package com.example.myapplication.view.fragments.main


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import com.example.myapplication.itemsRow.ChatItemFrom
import com.example.myapplication.itemsRow.ChatItemTo
import com.example.myapplication.itemsRow.LatestMessage
import com.example.myapplication.model.ChatMessage
import com.example.myapplication.model.User
import com.example.myapplication.view.MainActivity.Companion.currentUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_chat_log.*


class ChatLogFragment : Fragment() {

    lateinit var adapter: GroupAdapter<ViewHolder>
    lateinit var toUser: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat_log, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setTitle()
        setInitialData()
        messageListener()
        initListener()
    }

    private fun setTitle() {
        val bundle = arguments
        val user = bundle!!.getSerializable("User") as User
        activity!!.title = user.firstname
    }

    private fun setInitialData() {
        adapter = GroupAdapter()
        rvChatLogMessages.adapter = adapter
        val bundle = arguments
        toUser = bundle!!.getSerializable("User") as User
    }

    private fun messageListener() {
        val fromId = FirebaseAuth.getInstance().uid
        val toId = toUser.uid
        val messageRef = FirebaseDatabase.getInstance().getReference("/user-messages/$fromId/$toId")
        messageRef.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val message = p0.getValue(ChatMessage::class.java)
                if (message != null) {
                    if (message.fromId == FirebaseAuth.getInstance().uid) {
                        adapter.add(ChatItemFrom(message, currentUser!!))
                    } else {
                        adapter.add(ChatItemTo(message, toUser))
                    }
                }
                rvChatLogMessages.scrollToPosition(adapter.itemCount - 1)
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
            }

            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildRemoved(p0: DataSnapshot) {
            }
        })
    }

    private fun initListener() {
        btnSendMessage.setOnClickListener {
            performSendMessage()
        }
    }

    private fun performSendMessage() {
        val text = etChatLogMessage.text.toString()
        val fromId = currentUser?.uid
        val toId = toUser.uid
        val currentTime = System.currentTimeMillis()

        val messageRefFrom = FirebaseDatabase.getInstance().getReference("/user-messages/$fromId/$toId").push()
        val messageRefTo = FirebaseDatabase.getInstance().getReference("/user-messages/$toId/$fromId").push()

        if (fromId == null) return

        val chatMessage = ChatMessage(messageRefFrom.key!!, text, fromId, toId, currentTime)

        messageRefFrom.setValue(chatMessage)
            .addOnSuccessListener {
                rvChatLogMessages.scrollToPosition(adapter.itemCount - 1)
            }
        messageRefTo.setValue(chatMessage)


        val latestMessageRef = FirebaseDatabase.getInstance().getReference("/latest_messages/$fromId/$toId")
        latestMessageRef.setValue(chatMessage)
        val latestMessageToRef = FirebaseDatabase.getInstance().getReference("/latest_messages/$toId/$fromId")
        latestMessageToRef.setValue(chatMessage)

        etChatLogMessage.text.clear()
    }
}