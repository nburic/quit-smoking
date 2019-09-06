package com.example.sampleapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


class MainViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    companion object {
        private const val CHILD_FRAGMENTS_COUNT = 3
    }

    override fun getItem(position: Int): Fragment {
        return MainFragment()
    }

    override fun getCount() = CHILD_FRAGMENTS_COUNT




}