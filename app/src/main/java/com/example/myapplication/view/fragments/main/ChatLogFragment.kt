package com.example.myapplication.view.fragments.main


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import com.example.myapplication.adapter.ChatItemFrom
import com.example.myapplication.adapter.ChatItemTo
import com.example.myapplication.model.ChatMessage
import com.example.myapplication.model.User
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
//        chatDummyData()
        messageListener()
        initListener()
    }

    private fun messageListener() {
        val messageRef = FirebaseDatabase.getInstance().getReference("/messages")
        messageRef.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val message = p0.getValue(ChatMessage::class.java)
                if (message != null) {
                    if (message.fromid == FirebaseAuth.getInstance().uid) {
                        adapter.add(ChatItemFrom(message.text))
                    } else {
                        adapter.add(ChatItemTo(message.text))
                    }
                }
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

    private fun setInitialData() {
        adapter = GroupAdapter()
        rvChatLogMessages.adapter = adapter
    }

    private fun initListener() {
        btnSendMessage.setOnClickListener {
            performSendMessage()
            etChatLogMessage.text.clear()
        }
    }

    private fun performSendMessage() {
        val messageRef = FirebaseDatabase.getInstance().getReference("/messages").push()
        val text = etChatLogMessage.text.toString()
        val fromId = FirebaseAuth.getInstance().uid
        val bundle = arguments
        val user = bundle!!.getSerializable("User") as User
        val toId = user.uid

        if (fromId == null) return

        val chatMessage = ChatMessage(messageRef.key!!, text, fromId, toId, System.currentTimeMillis())
        messageRef.setValue(chatMessage)
            .addOnCompleteListener {
                Log.d("ChatLog", messageRef.key)
            }
    }

    private fun setTitle() {
        val bundle = arguments
        val user = bundle!!.getSerializable("User") as User
        activity!!.title = user.firstname
    }

    private fun chatDummyData() {
        adapter.add(ChatItemTo("Hi"))
        adapter.add(ChatItemFrom("Hello"))
        adapter.add(ChatItemTo("Message 1"))
        adapter.add(ChatItemTo("Second\nmessage"))
        adapter.add(ChatItemFrom("Last\ntext\nmessage"))
    }
}
