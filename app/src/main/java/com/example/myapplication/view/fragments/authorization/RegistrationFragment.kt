package com.example.myapplication.view.fragments.authorization


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.myapplication.R
import com.example.myapplication.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_registration.*
import java.util.*

class RegistrationFragment : Fragment() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: FirebaseDatabase
    val TAG = "Main_Register"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setInitialData()
        initListener()
    }

    private fun setInitialData() {
        mAuth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance()
    }

    private fun initListener() {
        tvLoginFromRegistration.setOnClickListener {
            setLoginFragment()
        }

        btnSelectPhoto.setOnClickListener {
            selectPhoto()
        }

        btnRegistration.setOnClickListener {
            registerNewUser()
        }
    }

    private fun selectPhoto() {
        val intent = Intent(
            Intent.ACTION_PICK,
            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(intent, 0)
    }

    private fun setLoginFragment() {
        fragmentManager!!.beginTransaction().replace(
            R.id.authorization_container,
            LoginFragment()
        ).commit()
    }

    private fun registerNewUser() {
        val firstname = etRegisterFirstname.text.toString()
        val lastname = etRegisterLastname.text.toString()
        val email = etRegisterEmail.text.toString()
        val password = etRegisterPassword.text.toString()
        val passwordVerify = etRegisterPasswordVerification.text.toString()

        if (password != passwordVerify) {
            Toast.makeText(activity, "Passwords is not the same", Toast.LENGTH_SHORT).show()
            return
        }

        if (email.isEmpty() || password.isEmpty() || firstname.isEmpty() || lastname.isEmpty()) {
            Toast.makeText(activity, "Please enter all fields", Toast.LENGTH_SHORT).show()
            return
        }

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                Log.d(TAG,"Success create profile")
                uploadImageToFirebase()
            }
    }

    var selectedPhotoUri: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        selectedPhotoUri = data?.data

        val contentResolver = activity?.contentResolver
        val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)
        civRegisterImage.setImageBitmap(bitmap)
        btnSelectPhoto.alpha = 0f
    }

    private fun uploadImageToFirebase() {
        if (selectedPhotoUri == null) {
            return
        }

        val filename = UUID.randomUUID().toString()
        val storage = FirebaseStorage.getInstance().getReference("/images/$filename")

        storage.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                Log.d(TAG, "image successfully uploaded: ${it.metadata!!.path}")
                storage.downloadUrl.addOnSuccessListener {
                    saveUserToFirebaseDatabase(it.toString())
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "Fail image upload")
            }
    }

    private fun saveUserToFirebaseDatabase(profileImageUrl: String) {
        val uid = mAuth.uid ?: ""
        val userRef = db.getReference("/users/$uid")

        val firstname = etRegisterFirstname.text.toString()
        val lastname = etRegisterLastname.text.toString()

        val user = User(uid, firstname, lastname, profileImageUrl)
        userRef.setValue(user)
            .addOnSuccessListener {
                Toast.makeText(activity, "Registration was successful", Toast.LENGTH_SHORT).show()
                setLoginFragment()
            }
            .addOnFailureListener {
                Log.d(TAG, "Fail to save user into database")
            }
    }
}
