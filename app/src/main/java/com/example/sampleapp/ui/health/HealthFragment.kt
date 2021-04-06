package com.example.sampleapp.ui.health

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleapp.MainViewModel
import com.example.sampleapp.R
import com.example.sampleapp.data.models.health.HealthAchievementItem
import com.example.sampleapp.databinding.FragmentHealthBinding

class HealthFragment : Fragment() {

    private var _binding: FragmentHealthBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<MainViewModel>()

    private var healthItems: MutableList<HealthAchievementItem> = mutableListOf()

    private var adapter: AdapterCardsHealth = AdapterCardsHealth()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHealthBinding.inflate(inflater, container, false)

        binding.recycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@HealthFragment.adapter
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                    outRect.apply {
                        top = requireContext().resources.getDimension(R.dimen.health_list_item_spacing_half).toInt()
                        bottom = requireContext().resources.getDimension(R.dimen.health_list_item_spacing_half).toInt()
                        left = requireContext().resources.getDimension(R.dimen.health_list_item_spacing).toInt()
                        right = requireContext().resources.getDimension(R.dimen.health_list_item_spacing).toInt()
                    }
                }
            })
        }

        setCards(viewModel.getStartEpoch())
        adapter.setItems(healthItems)

        return binding.root
    }

    private fun setCards(startDate: Long) {
        for (i in 0..12) {
            val card = HealthAchievementItem()

            context?.let { ctx ->
                card.setCardData(ctx, i, startDate)
            }

            healthItems.add(card)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}