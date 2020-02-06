package com.example.sampleapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleapp.AdapterCardHistory
import com.example.sampleapp.AdapterCardStats
import com.example.sampleapp.GoalDialogFragment
import com.example.sampleapp.R
import com.example.sampleapp.db.User
import com.example.sampleapp.models.ProgressHistoryItem
import com.example.sampleapp.models.ProgressStatsItem
import com.example.sampleapp.util.empty
import com.example.sampleapp.views.ProgressCardView
import timber.log.Timber

class ProgressFragment : Fragment(), GoalDialogFragment.Listener {

    var goalItems: List<String> = listOf()

    private var statsItems: List<ProgressStatsItem> = listOf()

    private var historyItems: List<ProgressHistoryItem> = listOf()

    private lateinit var recyclerViewStats: RecyclerView
    private var viewAdapterStats: AdapterCardStats = AdapterCardStats(statsItems)

    private lateinit var recyclerViewHistory: RecyclerView
    private var viewAdapterHistory: AdapterCardHistory = AdapterCardHistory(historyItems)

    private lateinit var progressCardView: ProgressCardView

    private val viewModel by viewModels<ProgressViewModel>()

    private val userObserver = Observer { user: User? ->
        user ?: return@Observer
        onUserDataChanged(user)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_progress, container, false)

        recyclerViewStats = view.findViewById(R.id.rv_stats)
        recyclerViewStats.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewAdapterStats
        }

        recyclerViewHistory = view.findViewById(R.id.rv_history)
        recyclerViewHistory.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewAdapterHistory
        }

        progressCardView = view.findViewById(R.id.progressCard)
        progressCardView.apply {
            onSelectGoalClick = this@ProgressFragment::openDialogSheet
        }

        viewModel.user.observe(viewLifecycleOwner, userObserver)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        statsItems = listOf(
            ProgressStatsItem(requireContext().resources.getString(R.string.money_saved_label), "879€", R.drawable.mp_ic_money),
            ProgressStatsItem(requireContext().resources.getString(R.string.life_regained_label), "10d 20h 20m 11s", R.drawable.mp_ic_sentiment_satisfied_black),
            ProgressStatsItem(requireContext().resources.getString(R.string.cigs_not_smoked_label), "2220", R.drawable.mp_ic_smoke_free_black)
        )

        historyItems = listOf(
            ProgressHistoryItem(requireContext().resources.getString(R.string.cigs_smoked_label), "000", R.drawable.mp_ic_cigarette),
            ProgressHistoryItem(requireContext().resources.getString(R.string.money_spent_label), "000", R.drawable.mp_ic_attach_money_black),
            ProgressHistoryItem(requireContext().resources.getString(R.string.life_lost_label), "20d 11h 4m 11s", R.drawable.mp_ic_sentiment_very_dissatisfied_black)
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

    private fun onUserDataChanged(user: User) {
        progressCardView.setProgressValue(viewModel.setDifference(user.date) ?: String.empty)

        val goalIndex = user.goalIndex ?: 0

        progressCardView.setGoalValue(goalItems[goalIndex])
        progressCardView.setGoalPercentage(viewModel.getGoalPercentage())

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
}
