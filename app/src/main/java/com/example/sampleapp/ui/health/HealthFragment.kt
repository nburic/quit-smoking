package com.example.sampleapp.ui.health

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleapp.R
import com.example.sampleapp.data.db.UserEntity
import com.example.sampleapp.data.models.health.HealthAchievementItem

class HealthFragment : Fragment() {

    companion object {
        fun newInstance(): HealthFragment {
            return HealthFragment()
        }
    }

    private var healthItems: MutableList<HealthAchievementItem> = mutableListOf()

    private lateinit var recyclerView: RecyclerView
    private var viewAdapter: AdapterCardsHealth = AdapterCardsHealth()

    private val viewModel by viewModels<HealthViewModel>()

    private val userObserver = Observer { userEntity: UserEntity? ->
        userEntity ?: return@Observer
        onUserChanged(userEntity)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_health, container, false)

        val decor = object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                outRect.apply {
                    top = requireContext().resources.getDimension(R.dimen.health_list_item_spacing_half).toInt()
                    bottom = requireContext().resources.getDimension(R.dimen.health_list_item_spacing_half).toInt()
                    left = requireContext().resources.getDimension(R.dimen.health_list_item_spacing).toInt()
                    right = requireContext().resources.getDimension(R.dimen.health_list_item_spacing).toInt()
                }
            }
        }

        recyclerView = view.findViewById(R.id.rv_health_cards)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewAdapter
            addItemDecoration(decor)
        }

//        viewModel.userEntity.observe(viewLifecycleOwner, userObserver)

        return view
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

    private fun onUserChanged(userEntity: UserEntity) {
        userEntity.start?.let { startDate ->
            setCards(startDate)
            viewAdapter.setItems(healthItems)
        }
    }
}