package com.example.myapplication.activities.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.myapplication.R
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initListener()

    }

    private fun initListener() {
        tvRegistrationFromLogin.setOnClickListener {
            fragmentManager!!.beginTransaction().replace(R.id.container, RegistrationFragment()).commit()
        }
    }
}
