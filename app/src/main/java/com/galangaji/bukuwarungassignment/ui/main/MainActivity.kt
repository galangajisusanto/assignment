package com.galangaji.bukuwarungassignment.ui.main

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.galangaji.bukuwarungassignment.R
import com.galangaji.bukuwarungassignment.ui.home.HomeFragment
import com.galangaji.bukuwarungassignment.ui.profile.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var navigationItemSelectedListener: BottomNavigationView.OnNavigationItemSelectedListener =
        object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.navigation_home -> {
                        openFragment(HomeFragment())
                        return true
                    }
                    R.id.navigation_profile -> {
                        openFragment(ProfileFragment())
                        return true
                    }
                }
                return false
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openFragment(HomeFragment())
        bottom_navigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener)
    }

    fun openFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}