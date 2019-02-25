package com.example.myapplication.view

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.myapplication.R
import com.example.myapplication.model.User
import com.example.myapplication.view.fragments.main.SettingsFragment
import com.example.myapplication.view.fragments.main.GalleryFragment
import com.example.myapplication.view.fragments.main.LatestMessagesFragment
import com.example.myapplication.view.fragments.main.PeoplesFragment
import com.example.myapplication.view.fragments.main.UserProfileFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    companion object {
        var currentUser: User? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        setInitialFragment()
        fetchCurrentUser()

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

    }

    private fun fetchCurrentUser() {
        val uid = FirebaseAuth.getInstance().uid
        val userRef = FirebaseDatabase.getInstance().getReference("/users/$uid")
        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                currentUser = p0.getValue(User::class.java)!!
            }

            override fun onCancelled(p0: DatabaseError) {
            }
        })
    }

    private fun setInitialFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_container, UserProfileFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_user -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, UserProfileFragment())
                    .addToBackStack(null)
                    .commit()
            }
            R.id.nav_gallery -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, GalleryFragment())
                    .addToBackStack(null)
                    .commit()
            }
            R.id.nav_message -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, LatestMessagesFragment())
                    .addToBackStack(null)
                    .commit()
            }
            R.id.nav_peoples -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, PeoplesFragment())
                    .addToBackStack(null)
                    .commit()
            }
            R.id.nav_settings -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, SettingsFragment())
                    .addToBackStack(null)
                    .commit()
            }
            R.id.nav_logout -> {
                FirebaseAuth.getInstance().signOut()
                finish()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
