package com.example.sampleapp.ui.home.goal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sampleapp.R
import com.example.sampleapp.databinding.FragmentGoalListDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class GoalDialogFragment : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "GoalDialogFragment"

        fun newInstance(): GoalDialogFragment {
            return GoalDialogFragment()
        }
    }

    private var _binding: FragmentGoalListDialogBinding? = null
    private val binding: FragmentGoalListDialogBinding get() = _binding!!

    var onGoalSelected: (position: Int) -> Unit = { throw NotImplementedError("onGoalSelected not implemented!") }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentGoalListDialogBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val items = listOf(
                resources.getString(R.string.goal_two_days),
                resources.getString(R.string.goal_three_days),
                resources.getString(R.string.goal_four_days),
                resources.getString(R.string.goal_five_days),
                resources.getString(R.string.goal_six_days),
                resources.getString(R.string.goal_one_week),
                resources.getString(R.string.goal_ten_days),
                resources.getString(R.string.goal_two_weeks),
                resources.getString(R.string.goal_three_weeks),
                resources.getString(R.string.goal_one_month),
                resources.getString(R.string.goal_three_months),
                resources.getString(R.string.goal_six_months),
                resources.getString(R.string.goal_one_year),
                resources.getString(R.string.goal_five_years)
        )

        binding.recycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = AdapterGoals(items, this@GoalDialogFragment::onItemClicked)
        }
    }

    private fun onItemClicked(position: Int) {
        onGoalSelected.invoke(position)
        dismiss()
    }
}
