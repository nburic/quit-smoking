package com.example.sampleapp.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

// Inflate Method corresponding to the specified Binding Class Type
// needed to inflate the binding in onCreateView
open class BaseFragment<T : ViewBinding>(private val inflateMethod : (LayoutInflater, ViewGroup?, Boolean) -> T): Fragment() {

    private var _binding : T? = null
    // This can be accessed by the child fragments
    // Only valid between onCreateView and onDestroyView
    val binding : T get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = inflateMethod.invoke(inflater, container, false)

        // replaced _binding!! with binding
        return binding.root
    }

    // Removing the binding reference when not needed is recommended as it avoids memory leak
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}