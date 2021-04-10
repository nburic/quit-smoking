package com.example.sampleapp.ui.store

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleapp.MainViewModel
import com.example.sampleapp.data.db.store.StoreItemEntity
import com.example.sampleapp.data.db.user.UserEntity
import com.example.sampleapp.databinding.FragmentStoreBinding
import com.example.sampleapp.ui.dialog.AddItemDialogFragment
import com.example.sampleapp.ui.settings.SettingsFragment
import com.example.sampleapp.util.Epoch
import com.example.sampleapp.util.toPx


class StoreFragment : Fragment() {

    private var _binding: FragmentStoreBinding? = null
    private val binding: FragmentStoreBinding get() = _binding!!

    private val viewModel by activityViewModels<MainViewModel>()

    private val adapter = AdapterStoreItems(this::onDeleteItem)

    private val userObserver = Observer { user: UserEntity? ->
        user ?: return@Observer
        onUserDataChanged(user)
    }

    private val storeObserver = Observer { store: List<StoreItemEntity>? ->
        store ?: return@Observer
        onStoreDataChanged(store)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentStoreBinding.inflate(inflater, container, false)

        viewModel.user.observe(viewLifecycleOwner, userObserver)
        viewModel.store.observe(viewLifecycleOwner, storeObserver)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@StoreFragment.adapter
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                    outRect.top = 5.toPx(context)
                    outRect.bottom = 5.toPx(context)
                    outRect.left = 10.toPx(context)
                    outRect.right = 10.toPx(context)
                }
            })
        }

        binding.fabAddItem.setOnClickListener {
            val dialog = AddItemDialogFragment()
            dialog.onSubmitClick = this::addItem

            dialog.show(childFragmentManager, AddItemDialogFragment.TAG)
        }
    }

    private fun addItem(name: String, price: Int) {
        viewModel.addStoreItem(StoreItemEntity(id = 0, title = name, price = price))
    }

    private fun onUserDataChanged(user: UserEntity) {
        val moneySaved = "${Epoch.calcMoney(Epoch.calcDifferenceToDays(user.start), user.cigPerDay, user.inPack, user.price)} ${SettingsFragment.CURRENCY}"

        binding.tvCurrentMoney.text = moneySaved
    }

    private fun onStoreDataChanged(store: List<StoreItemEntity>) {
        adapter.setItems(store)
    }

    private fun onDeleteItem(item: StoreItemEntity) {
        viewModel.removeStoreItem(item.id)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}