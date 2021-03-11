package com.example.sampleapp

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleapp.data.db.UserEntity
import com.example.sampleapp.models.SettingsInputItem
import com.example.sampleapp.models.SettingsInputItemType
import com.example.sampleapp.ui.DatePickerFragment
import com.example.sampleapp.util.DateConverters


class SettingsActivity : AppCompatActivity() {

//    private lateinit var toolbar: Toolbar
//    private lateinit var spinnerCurrency: Spinner
//    private lateinit var btnSubmit: Button
//
//    private lateinit var tvDate: TextView
//    private lateinit var recyclerView: RecyclerView
//    private var adapter: AdapterSettingsInput? = null
//
////    private val viewModel by viewModels<SettingsViewModel>()
//
//    private val stateObserver = Observer { state: SettingsViewModel.State? ->
//        state ?: return@Observer
//        onModelStateChanged(state)
//    }
//
//    private val userObserver = Observer { userEntity: UserEntity? ->
//        userEntity ?: return@Observer
//        onUserDataChanged(userEntity)
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_settings)
//
//        viewModel.state.observe(this, stateObserver)
//        viewModel.userEntity.observe(this, userObserver)
//
//        toolbar = findViewById(R.id.toolbar_settings)
//        setSupportActionBar(toolbar)
//
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setDisplayShowHomeEnabled(true)
//
//        spinnerCurrency = findViewById(R.id.spinner_currency)
//
//        tvDate = findViewById(R.id.tv_date)
//        tvDate.setOnClickListener {
//            DatePickerFragment.newInstance().apply {
//                dateSet = this@SettingsActivity::onDateSetChanged
//                show(supportFragmentManager, DatePickerFragment.TAG)
//            }
//        }
//
//        adapter = AdapterSettingsInput(onIncrement = viewModel::incInputValue, onDecrement = viewModel::decInputValue)
//
//        recyclerView = findViewById(R.id.rv_settings)
//        recyclerView.apply {
//            layoutManager = LinearLayoutManager(context)
//            adapter = this@SettingsActivity.adapter
//        }
//
//        btnSubmit = findViewById(R.id.btn_submit_stats)
//        btnSubmit.setOnClickListener {
//
//            viewModel.setState(SettingsViewModel.State.Loading)
//            val items = adapter?.getItems() ?: emptyList()
//
//            val date = viewModel.dateTimestamp.value ?: return@setOnClickListener
//            val perDay = items.find { it.type == SettingsInputItemType.PER_DAY }?.value
//                    ?: return@setOnClickListener
//            val inPack = items.find { it.type == SettingsInputItemType.IN_PACK }?.value
//                    ?: return@setOnClickListener
//            val years = items.find { it.type == SettingsInputItemType.YEARS }?.value
//                    ?: return@setOnClickListener
//            val price = items.find { it.type == SettingsInputItemType.PRICE }?.value
//                    ?: return@setOnClickListener
//
//            val user = UserEntity(
//                    uid = 0L,
//                    start = date,
//                    cigPerDay = perDay,
//                    inPack = inPack,
//                    years = years,
//                    price = price.toFloat(),
//                    goal = DateConverters.getEndTimestamp(date, 2, DateConverters.Duration.DAYS)
//            )
//
//            viewModel.setUserData(user)
//            viewModel.setState(SettingsViewModel.State.Done)
//
//            setResult(Activity.RESULT_OK)
//            finish()
//        }
//    }
//
//    private fun onUserDataChanged(userEntity: UserEntity) {
//        viewModel.dateTimestamp.value = viewModel.userEntity.value?.start
//        val perDay = viewModel.userEntity.value?.cigPerDay
//        val inPack = viewModel.userEntity.value?.inPack
//        val years = viewModel.userEntity.value?.years?.toInt()
//        val price = viewModel.userEntity.value?.price?.toInt()
//
//        tvDate.text = DateConverters.fromTimestamp(userEntity.start).toString()
//        adapter?.setItems(listOf(
//                SettingsInputItem(resources.getString(R.string.settings_smoked_per_day_label), SettingsInputItemType.PER_DAY, perDay),
//                SettingsInputItem(resources.getString(R.string.settings_cigs_in_pack_label), SettingsInputItemType.IN_PACK, inPack),
//                SettingsInputItem(resources.getString(R.string.settings_years_of_smoking_label), SettingsInputItemType.YEARS, years),
//                SettingsInputItem(resources.getString(R.string.settings_price_per_pack_label), SettingsInputItemType.PRICE, price))
//        )
//    }
//
//    private fun onDateSetChanged(timestamp: Long) {
//        viewModel.dateTimestamp.value = timestamp
//        tvDate.text = DateConverters.fromTimestamp(timestamp).toString()
//    }
//
//    private fun onModelStateChanged(state: SettingsViewModel.State) {
//        when (state) {
//            SettingsViewModel.State.Done -> {
//                Log.d("!!!", "!!!State done")
//            }
//            SettingsViewModel.State.Loading -> {
//                Log.d("!!!", "!!!State loading")
//            }
//            SettingsViewModel.State.Error -> {
//                Log.d("!!!", "!!!State error")
//            }
//        }
//    }
}