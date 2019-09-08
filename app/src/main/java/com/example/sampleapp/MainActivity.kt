package com.example.sampleapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewPagerAdapter = MainViewPagerAdapter(supportFragmentManager)

        viewPager = findViewById(R.id.vp_main)
        viewPager.adapter = viewPagerAdapter

        tabLayout = findViewById(R.id.tl_main)
        tabLayout.setupWithViewPager(viewPager)
    }
}
