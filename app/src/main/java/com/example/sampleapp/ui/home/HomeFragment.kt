package com.example.sampleapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sampleapp.*
import com.example.sampleapp.data.db.UserEntity
import com.example.sampleapp.data.models.home.ProgressHistoryItem
import com.example.sampleapp.data.models.home.ProgressStatsItem
import com.example.sampleapp.databinding.FragmentHomeBinding
import com.example.sampleapp.ui.settings.SettingsFragment.Companion.CURRENCY
import com.example.sampleapp.util.Epoch.calcDifferenceToDays
import com.example.sampleapp.util.Epoch.calcLifeLost
import com.example.sampleapp.util.Epoch.calcLifeRegained
import com.example.sampleapp.util.Epoch.calcMoney
import com.example.sampleapp.util.Epoch.calcNotSmoked
import com.example.sampleapp.util.Epoch.calcPassedTime
import com.example.sampleapp.util.Epoch.calcPercentage
import com.example.sampleapp.util.Epoch.calcSmoked
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment(), GoalDialogFragment.Listener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<MainViewModel>()

    private val adapterStats = AdapterCardStats()
    private val adapterHistory = AdapterCardHistory()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        viewModel.user.observe(viewLifecycleOwner, { user: UserEntity? ->
            when (user) {
                null -> findNavController().navigate(HomeFragmentDirections.actionGlobalInclusiveSettingsFragment())
                else -> onUserDataChanged(user)
            }
        })

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvStats.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterStats
        }

        binding.rvHistory.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterHistory
        }

        binding.progressCard.apply {
            onSelectGoalClick = this@HomeFragment::openDialogSheet
        }
    }

    private fun onUserDataChanged(userEntity: UserEntity) {
        binding.progressCard.setProgressValue(calcPassedTime(userEntity.start))
        binding.progressCard.setGoalPercentage(calcPercentage(userEntity.start, userEntity.goal))

        val smoked = calcSmoked(userEntity.years, userEntity.cigPerDay).toString()
        val notSmoked = calcNotSmoked(userEntity.start, userEntity.cigPerDay).toString()
        val moneySpent = "${calcMoney(userEntity.years * 365, userEntity.cigPerDay, userEntity.inPack, userEntity.price)} $CURRENCY"
        val moneySaved = "${calcMoney(calcDifferenceToDays(userEntity.start), userEntity.cigPerDay, userEntity.inPack, userEntity.price)} $CURRENCY"
        val lifeRegained = calcLifeRegained(notSmoked.toInt())
        val lifeLost = calcLifeLost(smoked.toInt())

        val stats = listOf(
                ProgressStatsItem(resources.getString(R.string.mp_money_saved_label), moneySaved, R.drawable.mp_ic_money),
                ProgressStatsItem(resources.getString(R.string.mp_life_regained_label), lifeRegained, R.drawable.mp_ic_sentiment_satisfied_black),
                ProgressStatsItem(resources.getString(R.string.mp_cigs_not_smoked_label), notSmoked, R.drawable.mp_ic_smoke_free_black)
        )

        val history = listOf(
                ProgressHistoryItem(resources.getString(R.string.mp_cigs_smoked_label), smoked, R.drawable.mp_ic_cigarette),
                ProgressHistoryItem(resources.getString(R.string.mp_money_spent_label), moneySpent, R.drawable.mp_ic_attach_money_black),
                ProgressHistoryItem(resources.getString(R.string.mp_life_lost_label), lifeLost, R.drawable.mp_ic_sentiment_very_dissatisfied_black)
        )

        adapterStats.setItems(stats)
        adapterHistory.setItems(history)
    }

    private fun openDialogSheet() {
        GoalDialogFragment.newInstance().show(childFragmentManager, GoalDialogFragment.TAG)
    }

    override fun onGoalClicked(position: Int) {
        viewModel.setGoal(position)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
