package com.example.sampleapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleapp.AdapterCardHistory
import com.example.sampleapp.AdapterCardStats
import com.example.sampleapp.R
import com.example.sampleapp.models.ProgressHistoryItem
import com.example.sampleapp.models.ProgressStatsItem

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
        ProgressHistoryItem(R.drawable.mp_ic_cigarette, "1244"),
        ProgressHistoryItem(R.drawable.mp_ic_attach_money_black, "3000€"),
        ProgressHistoryItem(R.drawable.mp_ic_sentiment_very_dissatisfied_black, "20d 11h 4m 11s")
    )

    private lateinit var recyclerViewStats: RecyclerView
    private var viewAdapterStats: AdapterCardStats = AdapterCardStats(statsItems)

    private lateinit var recyclerViewHistory: RecyclerView
    private var viewAdapterHistory: AdapterCardHistory = AdapterCardHistory(historyItems)

    private lateinit var viewModel: ProgressViewModel

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

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(ProgressViewModel::class.java)
    }

}
