package com.example.myapplication.view.fragments.authorization


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.myapplication.R
import com.example.myapplication.view.MainActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {


    lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setInitialData()
        initListener()

        // for faster login (testing function)
        setLoginAndPassword()
    }

    private fun setLoginAndPassword() {
        etLoginEmail.setText("user0@gmail.com")
        etLoginPassword.setText("123123")
    }

    private fun setInitialData() {
        mAuth = FirebaseAuth.getInstance()
    }

    private fun initListener() {
        tvRegistrationFromLogin.setOnClickListener {
            fragmentManager!!.beginTransaction().replace(
                R.id.authorization_container,
                RegistrationFragment()
            ).commit()
        }

        btnLogin.setOnClickListener {
            loginUser()
        }
    }

    private fun loginUser() {

        val email = etLoginEmail.text.toString()
        val password = etLoginPassword.text.toString()

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                activity!!.startActivity(Intent(this.context, MainActivity::class.java))
            }
            .addOnFailureListener {
                Toast.makeText(activity, "Wrong email/password", Toast.LENGTH_SHORT).show()
            }
    }
}
