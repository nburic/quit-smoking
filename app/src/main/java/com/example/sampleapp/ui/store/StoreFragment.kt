package com.example.sampleapp.ui.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.sampleapp.MainViewModel
import com.example.sampleapp.data.db.UserEntity
import com.example.sampleapp.databinding.FragmentStoreBinding
import com.example.sampleapp.ui.settings.SettingsFragment
import com.example.sampleapp.util.Epoch


class StoreFragment : Fragment() {

    private var _binding: FragmentStoreBinding? = null
    private val binding: FragmentStoreBinding get() = _binding!!

    private val viewModel by activityViewModels<MainViewModel>()

    private val userObserver = Observer { user: UserEntity? ->
        user ?: return@Observer
        onUserDataChanged(user)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentStoreBinding.inflate(inflater, container, false)

        viewModel.user.observe(viewLifecycleOwner, userObserver)

        return binding.root
    }

    private fun onUserDataChanged(user: UserEntity) {
        val moneySaved = "${Epoch.calcMoney(Epoch.calcDifferenceToDays(user.start), user.cigPerDay, user.inPack, user.price)} ${SettingsFragment.CURRENCY}"

        binding.tvCurrentMoney.text = moneySaved
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}