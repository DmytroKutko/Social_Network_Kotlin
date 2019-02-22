package com.example.myapplication.view.fragments.main


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import com.example.myapplication.adapter.ChatItemFrom
import com.example.myapplication.adapter.ChatItemTo
import com.example.myapplication.model.User
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_chat_log.*


class ChatLogFragment : Fragment() {

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
        initRecyclerView()
    }

    private fun setTitle() {
        val bundle = arguments
        val user = bundle!!.getSerializable("User") as User
        activity?.title = user.firstname
    }

    private fun initRecyclerView() {
        val adapter = GroupAdapter<ViewHolder>()
        adapter.add(ChatItemTo())
        adapter.add(ChatItemFrom())
        adapter.add(ChatItemTo())
        adapter.add(ChatItemTo())
        adapter.add(ChatItemFrom())
        rvChatLogMessages.adapter = adapter
    }
}
