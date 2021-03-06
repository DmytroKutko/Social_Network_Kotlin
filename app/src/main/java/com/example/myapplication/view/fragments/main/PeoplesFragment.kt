package com.example.myapplication.view.fragments.main


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import com.example.myapplication.itemsRow.UserItem
import com.example.myapplication.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_peoples.*


class PeoplesFragment : Fragment() {

    var db: FirebaseDatabase? = null
    var mAuth: FirebaseAuth? = null
    var adapter = GroupAdapter<ViewHolder>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_peoples, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setInitialData()
        fetchUsers()
    }

    private fun fetchUsers() {
        val userRef = db!!.getReference("/users")
        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {


                p0.children.forEach {
                    val user = it.getValue(User::class.java)
                    if (user?.uid != mAuth?.uid)
                        adapter.add(UserItem(user!!))
                }

                adapter.setOnItemClickListener { item, view ->

                    val peoplesFrag = activity!!.supportFragmentManager.beginTransaction()
                    val chatLogFrag = ChatLogFragment()

                    val bundle = Bundle()
                    val userItem = item as UserItem

                    bundle
                        .putSerializable("User", userItem.user)

                    chatLogFrag
                        .arguments = bundle

                    peoplesFrag.replace(R.id.main_container, chatLogFrag)
                        .commit()

                }

                rvPeoples.adapter = adapter
            }

            override fun onCancelled(p0: DatabaseError) {
            }

        })
    }

    private fun setInitialData() {
        db = FirebaseDatabase.getInstance()
        mAuth = FirebaseAuth.getInstance()
    }
}
