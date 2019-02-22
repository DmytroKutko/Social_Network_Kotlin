package com.example.myapplication.view.fragments.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import com.example.myapplication.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_user_profile.*

class UserProfileFragment : Fragment() {

    lateinit var db: FirebaseDatabase
    lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        db = FirebaseDatabase.getInstance()
        mAuth = FirebaseAuth.getInstance()
        val userId = mAuth.currentUser?.uid

        val userRef = db.getReference("/users/$userId")

        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                val user = p0.getValue(User::class.java)
                if (user != null) {
                    tvUserProfileFirstname.text = user.firstname
                    tvUserProfileLastname.text = user.lastname
                    Picasso.get().load(user.profileImage).into(civUserProfileImage)
                }
            }
        })
    }
}
