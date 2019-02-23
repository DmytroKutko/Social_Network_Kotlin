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
import com.google.firebase.database.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_chat_log.*


class ChatLogFragment : Fragment() {

    lateinit var adapter: GroupAdapter<ViewHolder>
    lateinit var toUser: User
    lateinit var currentUser: User

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
        fetchCurrentUser()
        adapter = GroupAdapter()
        rvChatLogMessages.adapter = adapter
        val bundle = arguments
        toUser = bundle!!.getSerializable("User") as User
    }

    private fun messageListener() {
        val messageRef = FirebaseDatabase.getInstance().getReference("/messages")
        messageRef.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val message = p0.getValue(ChatMessage::class.java)
                if (message != null) {
                    if (message.fromid == FirebaseAuth.getInstance().uid) {
                        adapter.add(ChatItemFrom(message, currentUser))
                    } else {
                        adapter.add(ChatItemTo(message, toUser))
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

    private fun fetchCurrentUser() {
        val uid = FirebaseAuth.getInstance().uid
        val userRef = FirebaseDatabase.getInstance().getReference("/users/$uid")
        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                currentUser = p0.getValue(User::class.java)!!
            }
            override fun onCancelled(p0: DatabaseError) {
            }
        })
    }
}