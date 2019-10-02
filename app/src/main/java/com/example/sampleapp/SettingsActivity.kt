package com.example.sampleapp

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleapp.db.User
import com.example.sampleapp.models.SettingsInputItem
import com.example.sampleapp.models.SettingsInputItemType
import com.example.sampleapp.ui.DatePickerFragment
import com.example.sampleapp.util.DateConverters


class SettingsActivity : AppCompatActivity() {

    private var inputItems = listOf(
        SettingsInputItem("[Cigarettes smoked per day]", SettingsInputItemType.PER_DAY),
        SettingsInputItem("[Cigarettes in a pack]", SettingsInputItemType.IN_PACK),
        SettingsInputItem("[Years of smoking]", SettingsInputItemType.YEARS),
        SettingsInputItem("[Price per pack]", SettingsInputItemType.PRICE)
    )

    private lateinit var toolbar: Toolbar
    private lateinit var spinnerCurrency: Spinner
    private lateinit var btnSubmit: Button

    private lateinit var tvDate: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: AdapterSettingsInput

    private lateinit var viewModel: SettingsViewModel

    private val stateObserver = Observer { state: SettingsViewModel.State? ->
        state ?: return@Observer
        onModelStateChanged(state)
    }

    private val userObserver = Observer { user: User? ->
        user ?: return@Observer
        onUserDataChanged(user)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        viewModel = ViewModelProviders.of(this).get(SettingsViewModel::class.java)
        viewModel.state.observe(this, stateObserver)
        viewModel.user.observe(this, userObserver)

        toolbar = findViewById(R.id.toolbar_settings)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        spinnerCurrency = findViewById(R.id.spinner_currency)

        ArrayAdapter.createFromResource(this, R.array.currencies_array, android.R.layout.simple_spinner_item).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinnerCurrency.adapter = adapter
        }

        tvDate = findViewById(R.id.tv_date)
        tvDate.setOnClickListener {
            DatePickerFragment.newInstance().apply {
                dateSet = this@SettingsActivity::onDateSetChanged
                show(supportFragmentManager, DatePickerFragment.TAG)
            }
        }

        viewAdapter = AdapterSettingsInput(inputItems, onIncrement = viewModel::incInputValue, onDecrement = viewModel::decInputValue)

        recyclerView = findViewById(R.id.rv_settings)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewAdapter
        }

        btnSubmit = findViewById(R.id.btn_submit_stats)
        btnSubmit.setOnClickListener {

            viewModel.setState(SettingsViewModel.State.Loading)

            val date = viewModel.dateTimestamp
            val perDay = inputItems[0].value?.toInt()
            val inPack = inputItems[1].value?.toInt()
            val years = inputItems[2].value?.toFloat()
            val price = inputItems[3].value?.toFloat()

            val user = User(uid = 0, date = date, perDay = perDay, inPack = inPack, years = years, price = price, currency = "EUR")

            viewModel.setUserData(user)
            viewModel.setState(SettingsViewModel.State.Done)

            finish()
        }
    }

    private fun onUserDataChanged(user: User) {
        tvDate.text = DateConverters.fromTimestamp(user.date).toString()

        inputItems.forEach { item ->
            when (item.type) {
                SettingsInputItemType.PER_DAY -> {
                    item.value = user.perDay?.toString()
                }
                SettingsInputItemType.IN_PACK -> {
                    item.value = user.inPack?.toString()
                }
                SettingsInputItemType.YEARS -> {
                    item.value = user.years?.toInt().toString()
                }
                SettingsInputItemType.PRICE -> {
                    item.value = user.price?.toInt().toString()
                }
            }
        }

        viewAdapter.setItems(inputItems)
    }

    private fun onDateSetChanged(timestamp: Long) {
        viewModel.dateTimestamp = timestamp
        tvDate.text = DateConverters.fromTimestamp(timestamp).toString()
    }

    private fun onModelStateChanged(state: SettingsViewModel.State) {
        when (state) {
            SettingsViewModel.State.Done -> {
                Log.d("!!!", "!!!State done")
            }
            SettingsViewModel.State.Loading -> {
                Log.d("!!!", "!!!State loading")
            }
            SettingsViewModel.State.Error -> {
                Log.d("!!!", "!!!State error")
            }
        }
    }
}