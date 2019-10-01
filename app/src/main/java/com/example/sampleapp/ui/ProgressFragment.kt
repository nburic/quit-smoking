package com.example.sampleapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleapp.AdapterCardStats
import com.example.sampleapp.R
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

    private lateinit var recyclerView: RecyclerView
    private var viewAdapter: AdapterCardStats = AdapterCardStats(statsItems)

    private lateinit var viewModel: ProgressViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_progress, container, false)

        recyclerView = view.findViewById(R.id.rv_stats)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewAdapter
        }

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(ProgressViewModel::class.java)
    }

}
