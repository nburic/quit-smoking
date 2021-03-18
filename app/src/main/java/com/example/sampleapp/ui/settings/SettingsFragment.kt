package com.example.sampleapp.ui.settings

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.sampleapp.MainViewModel
import com.example.sampleapp.data.db.UserEntity
import com.example.sampleapp.databinding.FragmentSettingsBinding
import com.example.sampleapp.util.DateConverters.toDateTime

class SettingsFragment : Fragment() {

    companion object {
        private const val CIG_PER_DAY_MAX = 60
        private const val CIG_PER_DAY_MIN = 1
        private const val CIG_PER_DAY_STEP = 1

        private const val CIG_IN_PACK_MAX = 200
        private const val CIG_IN_PACK_MIN = 10
        private const val CIG_IN_PACK_STEP = 10

        private const val YEARS_MAX = 50
        private const val YEARS_MIN = 1
        private const val YEARS_STEP = 1

        private const val PRICE_MAX = 20
        private const val PRICE_MIN = 1
        private const val PRICE_STEP = 0.2f

        private const val CURRENCY = "€"
    }

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        viewModel.user.observe(viewLifecycleOwner, { user: UserEntity? ->
            when (user) {
                null -> {
                    binding.tvDateValue.text = toDateTime(requireContext(), 0)
                    binding.tvCigPerDayValue.text = CIG_PER_DAY_MIN.toString()
                    binding.tvCigInPackValue.text = CIG_IN_PACK_MIN.toString()
                    binding.tvYearsValue.text = YEARS_MIN.toString()
                    binding.tvPriceValue.text = PRICE_MIN.toString()
                }
                else -> {
                    binding.tvDateValue.text = toDateTime(requireContext(), user.start)
                    binding.tvCigPerDayValue.text = user.cigPerDay.toString()
                    binding.tvCigInPackValue.text = user.inPack.toString()
                    binding.tvYearsValue.text = user.years.toString()
                    binding.tvPriceValue.text = user.price.toString()
                }
            }
        })

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

        setSeekBars()
    }

    private fun setSeekBars() {
        binding.sbCigPerDay.apply {
            max = (CIG_PER_DAY_MAX - CIG_PER_DAY_MIN) / CIG_PER_DAY_STEP
            setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    val customProgress = CIG_PER_DAY_MIN + (progress * CIG_PER_DAY_STEP)
                    binding.tvCigPerDayValue.text = customProgress.toString()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })
        }

        binding.sbCigInPack.apply {
            max = (CIG_IN_PACK_MAX - CIG_IN_PACK_MIN) / CIG_IN_PACK_STEP
            setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    val customProgress = CIG_IN_PACK_MIN + (progress * CIG_IN_PACK_STEP)
                    binding.tvCigInPackValue.text = customProgress.toString()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })
        }

        binding.sbYears.apply {
            max = (YEARS_MAX - YEARS_MIN) / YEARS_STEP
            setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    val customProgress = YEARS_MIN + (progress * YEARS_STEP)
                    binding.tvYearsValue.text = customProgress.toString()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })
        }

        binding.sbPrice.apply {
            max = ((PRICE_MAX - PRICE_MIN) / PRICE_STEP).toInt()
            setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                @SuppressLint("SetTextI18n")
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    val customProgress = PRICE_MIN + (progress * PRICE_STEP)
                    binding.tvPriceValue.text = "${String.format("%.2f", customProgress)} $CURRENCY"
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })
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