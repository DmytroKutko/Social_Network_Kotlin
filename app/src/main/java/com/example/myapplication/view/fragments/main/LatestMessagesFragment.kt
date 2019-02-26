package com.example.myapplication.view.fragments.main


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import com.example.myapplication.itemsRow.LatestMessage
import com.example.myapplication.itemsRow.UserItem
import com.example.myapplication.model.ChatMessage
import com.example.myapplication.view.MainActivity.Companion.currentUser
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_latest_messages.*

class LatestMessagesFragment : Fragment() {

    lateinit var adapter: GroupAdapter<ViewHolder>
    var latestMessageMap = HashMap<String, ChatMessage>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_latest_messages, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setTitle()
        initRecyclerView()
        initListener()
        latestMessagesListener()
    }

    private fun initListener() {
        adapter.setOnItemClickListener { item, view ->
            val latestMessagesFrag = activity!!.supportFragmentManager.beginTransaction()
            val chatLogFrag = ChatLogFragment()

            val bundle = Bundle()
            val row = item as LatestMessage

            bundle
                .putSerializable("User", row.chatPartnerUser)

            chatLogFrag
                .arguments = bundle

            latestMessagesFrag.replace(R.id.main_container, chatLogFrag)
                .commit()
        }
    }

    private fun setTitle() {
        activity?.title = "Latest messages"
    }

    private fun initRecyclerView() {
        adapter = GroupAdapter()
        rvLatestMessages.adapter = adapter
        rvLatestMessages.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
    }

    private fun latestMessagesListener() {
        val fromId = currentUser?.uid
        val messageRef = FirebaseDatabase.getInstance().getReference("/latest_messages/$fromId")
        messageRef.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val chatMessage = p0.getValue(ChatMessage::class.java) ?: return
                latestMessageMap[p0.key!!] = chatMessage
                refreshAdapter()
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                val chatMessage = p0.getValue(ChatMessage::class.java) ?: return
                adapter.add(LatestMessage(chatMessage))
            }

            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildRemoved(p0: DataSnapshot) {
            }
        })
    }

    private fun refreshAdapter() {
        adapter.clear()
        latestMessageMap.forEach {
            adapter.add(LatestMessage(it.value))
        }
    }

}
