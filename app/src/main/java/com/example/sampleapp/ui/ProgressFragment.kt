package com.example.sampleapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleapp.AdapterCardHistory
import com.example.sampleapp.AdapterCardStats
import com.example.sampleapp.R
import com.example.sampleapp.db.User
import com.example.sampleapp.models.ProgressHistoryItem
import com.example.sampleapp.models.ProgressHistoryItemType
import com.example.sampleapp.models.ProgressStatsItem
import com.example.sampleapp.views.ProgressCardView

class ProgressFragment : Fragment() {

    companion object {
        fun newInstance(): ProgressFragment {
            return ProgressFragment()
        }
    }

    private val statsItems = listOf(
        ProgressStatsItem("[Money saved]", "879€", R.drawable.mp_ic_money),
        ProgressStatsItem("[Life regained]", "10d 20h 20m 11s", R.drawable.mp_ic_sentiment_satisfied_black),
        ProgressStatsItem("[Cigarettes not smoked]", "2220", R.drawable.mp_ic_smoke_free_black)
    )

    private val historyItems = listOf(
        ProgressHistoryItem(R.drawable.mp_ic_cigarette, "1244", ProgressHistoryItemType.SMOKE),
        ProgressHistoryItem(R.drawable.mp_ic_attach_money_black, "3000€", ProgressHistoryItemType.MONEY),
        ProgressHistoryItem(R.drawable.mp_ic_sentiment_very_dissatisfied_black, "20d 11h 4m 11s", ProgressHistoryItemType.LIFE)
    )

    private lateinit var recyclerViewStats: RecyclerView
    private var viewAdapterStats: AdapterCardStats = AdapterCardStats(statsItems)

    private lateinit var recyclerViewHistory: RecyclerView
    private var viewAdapterHistory: AdapterCardHistory = AdapterCardHistory(historyItems)

    private lateinit var progressCardView: ProgressCardView

    private lateinit var viewModel: ProgressViewModel

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

        viewModel.user.observe(this, userObserver)

        return view
    }

    private fun onUserDataChanged(user: User) {
        user.date?.let {
            progressCardView.setProgressValue(viewModel.setDifference(it))
            statsItems[0].value = "${viewModel.calculateSavedMoney()}€"
            viewAdapterStats.setItems(statsItems)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(ProgressViewModel::class.java)
    }

}
