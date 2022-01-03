package com.example.sampleapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sampleapp.*
import com.example.sampleapp.data.db.user.UserEntity
import com.example.sampleapp.data.models.home.ProgressHistoryItem
import com.example.sampleapp.data.models.home.ProgressStatsItem
import com.example.sampleapp.databinding.FragmentHomeBinding
import com.example.sampleapp.ui.base.BaseFragment
import com.example.sampleapp.ui.home.goal.GoalDialogFragment
import com.example.sampleapp.ui.settings.SettingsFragment.Companion.CURRENCY
import com.example.sampleapp.util.DateConverters.getGoalTimestamp
import com.example.sampleapp.util.DateConverters.getGoalValue
import com.example.sampleapp.util.Epoch
import com.example.sampleapp.util.Epoch.calcLifeLost
import com.example.sampleapp.util.Epoch.calcLifeRegained
import com.example.sampleapp.util.Epoch.calcMoney
import com.example.sampleapp.util.Epoch.calcNotSmoked
import com.example.sampleapp.util.Epoch.calcPassedTime
import com.example.sampleapp.util.Epoch.calcPercentage
import com.example.sampleapp.util.Epoch.calcSmoked
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit


class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel by activityViewModels<MainViewModel>()

    private val adapterStats = AdapterCardStats()
    private val adapterHistory = AdapterCardHistory()

    private var uiDisposable: Disposable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        viewModel.user.observe(viewLifecycleOwner, { user: UserEntity? ->
            when (user) {
                null -> findNavController().navigate(HomeFragmentDirections.actionGlobalInclusiveSettingsFragment())
                else -> onUserDataChanged(user)
            }
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvStats.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterStats
        }

        binding.rvHistory.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterHistory
        }

        binding.progressCard.apply {
            onSelectGoalClick = this@HomeFragment::openDialogSheet
        }
    }

    override fun onResume() {
        super.onResume()

        val user = viewModel.getUser() ?: return
        onUserDataChanged(user)
    }

    override fun onPause() {
        super.onPause()

        uiDisposable?.dispose()
    }

    private fun onUserDataChanged(userEntity: UserEntity) {
        uiDisposable = Observable.interval(1000, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.newThread()) // poll data on a background thread
            .observeOn(AndroidSchedulers.mainThread()) // populate UI on main thread
            .subscribe({
                binding.progressCard.setProgressValue(calcPassedTime(userEntity.start))
            }, {
                Timber.e(it)
            }) // your UI code

        binding.progressCard.apply {
            setGoalPercentage(calcPercentage(userEntity.start, userEntity.goal))
            setGoalValue(getGoalValue(requireContext(), userEntity.start, userEntity.goal))
        }

        val smoked = calcSmoked(userEntity.years, userEntity.cigPerDay).toString()
        val notSmoked = calcNotSmoked(userEntity.start, userEntity.cigPerDay).toString()
        val moneySpent = "${
            calcMoney(
                days = userEntity.years * 365,
                perDay = userEntity.cigPerDay,
                inPack = userEntity.inPack,
                price = userEntity.price
            )
        } $CURRENCY"
        val moneySaved = "${
            calcMoney(
                days = Epoch.differenceBetweenTimestampsInDays(Epoch.now(), userEntity.start),
                perDay = userEntity.cigPerDay,
                inPack = userEntity.inPack,
                price = userEntity.price
            )
        } $CURRENCY"
        val lifeRegained = calcLifeRegained(notSmoked.toInt())
        val lifeLost = calcLifeLost(smoked.toInt())

        val stats = listOf(
            ProgressStatsItem(resources.getString(R.string.mp_money_saved_label), moneySaved, R.drawable.mp_ic_money),
            ProgressStatsItem(resources.getString(R.string.mp_life_regained_label), lifeRegained, R.drawable.mp_ic_sentiment_satisfied_black),
            ProgressStatsItem(resources.getString(R.string.mp_cigs_not_smoked_label), notSmoked, R.drawable.mp_ic_smoke_free_black)
        )

        val history = listOf(
            ProgressHistoryItem(resources.getString(R.string.mp_cigs_smoked_label), smoked, R.drawable.mp_ic_cigarette),
            ProgressHistoryItem(resources.getString(R.string.mp_money_spent_label), moneySpent, R.drawable.mp_ic_attach_money_black),
            ProgressHistoryItem(resources.getString(R.string.mp_life_lost_label), lifeLost, R.drawable.mp_ic_sentiment_very_dissatisfied_black)
        )

        adapterStats.setItems(stats)
        adapterHistory.setItems(history)
    }

    private fun openDialogSheet() {
        val selectedIndex = viewModel.getGoal()
        val dialog = GoalDialogFragment.newInstance(selectedIndex)

        dialog.onGoalSelected = ::onGoalClicked
        dialog.show(childFragmentManager, GoalDialogFragment.TAG)
    }

    private fun onGoalClicked(position: Int) {
        viewModel.setGoal(position)

        val epoch = getGoalTimestamp(position, viewModel.getStartEpoch())

        if (epoch > Epoch.now()) {
            viewModel.setGoalNotification(epoch, requireContext())
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        uiDisposable?.dispose()
    }
}
