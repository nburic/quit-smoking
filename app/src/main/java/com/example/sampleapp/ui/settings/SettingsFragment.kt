package com.example.sampleapp.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.sampleapp.MainViewModel
import com.example.sampleapp.data.db.UserEntity
import com.example.sampleapp.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnDate.setOnClickListener {
            val dialog = DatePickerFragment.newInstance()

            dialog.apply {
                onDateSet = this@SettingsFragment::onDateSet
            }

            dialog.show(childFragmentManager, DatePickerFragment.TAG)
        }

        binding.btnSubmit.setOnClickListener {
            val user = createUser() ?: return@setOnClickListener
            viewModel.setUserData(user)
        }
    }

    private fun onDateSet(epoch: Long) {
        viewModel.setStartEpoch(epoch)
    }

    private fun createUser(): UserEntity? {
        val epoch = viewModel.getStartEpoch()
        if (epoch == 0L) return null

        val perDay = binding.sbCigPerDay.progress
        val inPack = binding.sbCigInPack.progress
        val years = binding.sbYears.progress
        val price = binding.sbPrice.progress

        return UserEntity(
                start = epoch,
                cigPerDay = perDay,
                inPack = inPack,
                years = years,
                price = price.toFloat()
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}