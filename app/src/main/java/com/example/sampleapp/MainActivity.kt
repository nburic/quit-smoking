package com.example.sampleapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.example.sampleapp.ui.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout
    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

//        viewPager = findViewById(R.id.vp_main)
//        viewPager.adapter = MainViewPagerAdapter(supportFragmentManager)
//        viewPager.currentItem = 1
//
//        tabLayout = findViewById(R.id.tl_main)
//        tabLayout.setupWithViewPager(viewPager)

        bottomNav = findViewById(R.id.nav_main)
        bottomNav.selectedItemId = R.id.mi_progress
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_settings, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            R.id.mi_settings -> {
//                 supportFragmentManager.beginTransaction(). add(SettingsFragment.newInstance(), SettingsFragment.TAG)

            }
        }

        return super.onOptionsItemSelected(item)
    }
}
