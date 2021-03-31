package com.example.sampleapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sampleapp.*
import com.example.sampleapp.data.db.UserEntity
import com.example.sampleapp.data.models.home.ProgressHistoryItem
import com.example.sampleapp.data.models.home.ProgressStatsItem
import com.example.sampleapp.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class HomeFragment : Fragment(), GoalDialogFragment.Listener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var goalItems: List<String> = listOf()

    private var statsItems: List<ProgressStatsItem> = listOf()

    private var historyItems: List<ProgressHistoryItem> = listOf()

    private var viewAdapterStats: AdapterCardStats = AdapterCardStats(statsItems)

    private var viewAdapterHistory: AdapterCardHistory = AdapterCardHistory(historyItems)

    private val viewModel by viewModels<ProgressViewModel>()
    private val mainViewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        mainViewModel.user.observe(viewLifecycleOwner, { user: UserEntity? ->
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
            adapter = viewAdapterStats
        }

        binding.rvHistory.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewAdapterHistory
        }

        binding.progressCard.apply {
            onSelectGoalClick = this@HomeFragment::openDialogSheet
        }

        statsItems = listOf(
                ProgressStatsItem(
                        requireContext().resources.getString(R.string.mp_money_saved_label),
                        "879€",
                        R.drawable.mp_ic_money
                ),
                ProgressStatsItem(
                        requireContext().resources.getString(R.string.mp_life_regained_label),
                        "10d 20h 20m 11s",
                        R.drawable.mp_ic_sentiment_satisfied_black
                ),
                ProgressStatsItem(
                        requireContext().resources.getString(R.string.mp_cigs_not_smoked_label),
                        "2220",
                        R.drawable.mp_ic_smoke_free_black
                )
        )

        historyItems = listOf(
                ProgressHistoryItem(
                        requireContext().resources.getString(R.string.mp_cigs_smoked_label),
                        "000",
                        R.drawable.mp_ic_cigarette
                ),
                ProgressHistoryItem(
                        requireContext().resources.getString(R.string.mp_money_spent_label),
                        "000",
                        R.drawable.mp_ic_attach_money_black
                ),
                ProgressHistoryItem(
                        requireContext().resources.getString(R.string.mp_life_lost_label),
                        "20d 11h 4m 11s",
                        R.drawable.mp_ic_sentiment_very_dissatisfied_black
                )
        )

        goalItems = listOf(
                requireContext().resources.getString(R.string.goal_two_days),
                requireContext().resources.getString(R.string.goal_three_days),
                requireContext().resources.getString(R.string.goal_four_days),
                requireContext().resources.getString(R.string.goal_five_days),
                requireContext().resources.getString(R.string.goal_six_days),
                requireContext().resources.getString(R.string.goal_one_week),
                requireContext().resources.getString(R.string.goal_ten_days),
                requireContext().resources.getString(R.string.goal_two_weeks),
                requireContext().resources.getString(R.string.goal_three_weeks),
                requireContext().resources.getString(R.string.goal_one_month),
                requireContext().resources.getString(R.string.goal_three_months),
                requireContext().resources.getString(R.string.goal_six_months),
                requireContext().resources.getString(R.string.goal_one_year),
                requireContext().resources.getString(R.string.goal_five_years)
        )
    }

    private fun onUserDataChanged(userEntity: UserEntity) {
        binding.progressCard.setProgressValue(viewModel.setDifference(userEntity.start))
        binding.progressCard.setGoalPercentage(mainViewModel.getGoalPercentage(userEntity))

        val smoked = viewModel.calculateSmoked()
        val notSmoked = viewModel.calculateNotSmoked()

        historyItems[0].value = "$smoked"
        historyItems[1].value = "${viewModel.calculateSpentMoney()}"
        historyItems[2].value = "${viewModel.calculateLifeLost(smoked)}"

        statsItems[0].value = "${viewModel.calculateSavedMoney()}€"
        statsItems[1].value = "${viewModel.calculateLifeRegained(notSmoked)}"
        statsItems[2].value = "$notSmoked"

        viewAdapterStats.setItems(statsItems)
        viewAdapterHistory.setItems(historyItems)

    }

    private fun openDialogSheet() {
        GoalDialogFragment.newInstance().show(childFragmentManager, GoalDialogFragment.TAG)
    }

    override fun onGoalClicked(position: Int) {
        Timber.d("goal clicked position = $position")
        viewModel.setGoal(position)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
