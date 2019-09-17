package com.example.sampleapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sampleapp.R

class ProgressFragment : Fragment() {

    companion object {
        fun newInstance(): ProgressFragment {
            return ProgressFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_progress, container, false)
    }

}
