package com.example.myapplication.activities.fragments.authorization


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.myapplication.R
import com.example.myapplication.activities.MainActivity
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_registration.*

class RegistrationFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initListener()
    }

    private fun initListener() {
        tvLoginFromRegistration.setOnClickListener {
            fragmentManager!!.beginTransaction().replace(
                R.id.authorization_container,
                LoginFragment()
            ).commit()
        }

        btnRegistration.setOnClickListener {
            startActivity(Intent(activity, MainActivity::class.java))
        }
    }
}
