package com.example.sampleapp.ui.trophies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.sampleapp.MainViewModel
import com.example.sampleapp.databinding.FragmentTrophiesBinding

class TrophiesFragment : Fragment() {

//    private inner class HeaderSection(group: Group) : Section(group) {
//        fun addAllItems(items: List<Item>) {
//            addAll(items)
//        }
//    }

    private var _binding: FragmentTrophiesBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<MainViewModel>()

//    private var adapter = GroupAdapter<GroupieViewHolder>().apply {
//        spanCount = 2
//    }

//    private val sections: MutableList<HeaderSection> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTrophiesBinding.inflate(inflater, container, false)

        initRecycler()

        return binding.root
    }

    private fun initRecycler() {
//        val lm = GridLayoutManager(requireContext(), adapter.spanCount).apply {
//            spanSizeLookup = adapter.spanSizeLookup
//        }
//
//        binding.recycler.apply {
//            layoutManager = lm
//            adapter = this@TrophiesFragment.adapter
//        }
//
//        setGroupieItems()
    }

//    private fun setGroupieItems() {
//        sections.clear()
//
//        val sectionOne = HeaderSection(HeaderItem(R.string.trophies_header_title_one))
//
//        val cigNotSmoked = viewModel.calculateNotSmoked()
//        val freeDays = viewModel.getSmokeFreeDays()
//        val regainedDays = viewModel.getRegainedDays()
//
//        sectionOne.addAllItems(listOf(
//                buildBadgeItem(R.drawable.cig_not_smoked_20, cigNotSmoked >= 20),
//                buildBadgeItem(R.drawable.cig_not_smoked_100, cigNotSmoked >= 100),
//                buildBadgeItem(R.drawable.cig_not_smoked_1000, cigNotSmoked >= 1000),
//                buildBadgeItem(R.drawable.cig_not_smoked_10000, cigNotSmoked >= 10000)
//        ))
//
//        val sectionTwo = HeaderSection(HeaderItem(R.string.trophies_header_title_two))
//
//        sectionTwo.addAllItems(listOf(
//                buildBadgeItem(R.drawable.smoke_free_days_1, freeDays > 1),
//                buildBadgeItem(R.drawable.smoke_free_days_3, freeDays > 3),
//                buildBadgeItem(R.drawable.smoke_free_days_5, freeDays > 5),
//                buildBadgeItem(R.drawable.smoke_free_days_7, freeDays > 7),
//                buildBadgeItem(R.drawable.smoke_free_days_10, freeDays > 10),
//                buildBadgeItem(R.drawable.smoke_free_days_14, freeDays > 14),
//                buildBadgeItem(R.drawable.smoke_free_days_30, freeDays > 30),
//                buildBadgeItem(R.drawable.smoke_free_days_90, freeDays > 90),
//                buildBadgeItem(R.drawable.smoke_free_days_180, freeDays > 180),
//                buildBadgeItem(R.drawable.smoke_free_days_365, freeDays > 365)
//        ))
//
//        val sectionThree = HeaderSection(HeaderItem(R.string.trophies_header_title_three))
//
//        sectionThree.addAllItems(listOf(
//                buildBadgeItem(R.drawable.life_regained_1, regainedDays > 1),
//                buildBadgeItem(R.drawable.life_regained_10, regainedDays > 10),
//                buildBadgeItem(R.drawable.life_regained_30, regainedDays > 30),
//                buildBadgeItem(R.drawable.life_regained_90, regainedDays > 90),
//                buildBadgeItem(R.drawable.life_regained_180, regainedDays > 180),
//                buildBadgeItem(R.drawable.life_regained_365, regainedDays > 365)
//        ))
//
//        sections.add(sectionOne)
//        sections.add(sectionTwo)
//        sections.add(sectionThree)
//
//        adapter.clear()
//        adapter.addAll(sections)
//    }
//
//    private fun buildBadgeItem(imgRes: Int, achieved: Boolean) = ImageItem(imgRes, achieved)

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
