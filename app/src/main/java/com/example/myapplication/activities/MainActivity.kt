package com.example.myapplication.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.myapplication.R
import com.example.myapplication.activities.fragments.main.SettingsFragment
import com.example.myapplication.activities.fragments.main.GalleryFragment
import com.example.myapplication.activities.fragments.main.LatestMessagesFragment
import com.example.myapplication.activities.fragments.main.PeoplesFragment
import com.example.myapplication.activities.fragments.main.UserProfileFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
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
                startActivity(Intent(this, AuthorizationActivity::class.java))
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
