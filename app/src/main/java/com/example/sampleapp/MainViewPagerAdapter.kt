package com.example.sampleapp

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


class MainViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    companion object {
        private const val CHILD_FRAGMENTS_COUNT = 3
    }

    override fun getItem(position: Int): Fragment? {
        return when (position) {
            0 -> TrophiesFragment.newInstance()
            1 -> ProgressFragment.newInstance()
            2 -> HealthFragment.newInstance()
            else -> null
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return "Page $position"
    }

    override fun getCount() = CHILD_FRAGMENTS_COUNT




}