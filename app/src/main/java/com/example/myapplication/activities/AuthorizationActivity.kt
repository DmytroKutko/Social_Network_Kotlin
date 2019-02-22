package com.example.myapplication.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.activities.fragments.authorization.LoginFragment

class AuthorizationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorization)

        setInitialFragment()
    }

    private fun setInitialFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.authorization_container, LoginFragment())
            .addToBackStack(null)
            .commit()
    }
}
