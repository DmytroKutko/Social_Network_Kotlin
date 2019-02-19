package com.example.myapplication.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.R
import com.example.myapplication.activities.fragments.LoginFragment

class AuthorizationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorization)

        supportFragmentManager.beginTransaction().replace(R.id.container, LoginFragment()).commit()
    }
}
