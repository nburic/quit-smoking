package com.example.sampleapp

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.sampleapp.db.User
import com.example.sampleapp.ui.DatePickerFragment
import com.example.sampleapp.views.SettingsInputView


class SettingsActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var spinnerCurrency: Spinner
    private lateinit var btnSubmit: Button

    internal lateinit var etDate: EditText

    private lateinit var inputPerDay: SettingsInputView
    private lateinit var inputInPack: SettingsInputView
    private lateinit var inputYears: SettingsInputView
    private lateinit var inputPrice: SettingsInputView

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

        etDate = findViewById(R.id.et_date)
        etDate.setOnClickListener {
            val fragment = DatePickerFragment()
            fragment.show(supportFragmentManager, "DATEPICKER")
        }

        inputPerDay = findViewById(R.id.input_cig_per_day)
        inputInPack = findViewById(R.id.input_cig_in_pack)
        inputYears = findViewById(R.id.input_years)
        inputPrice = findViewById(R.id.input_price)

        btnSubmit = findViewById(R.id.btn_submit_stats)
        btnSubmit.setOnClickListener {

            viewModel.setState(SettingsViewModel.State.Loading)

            val perDay = inputPerDay.getValue()
            val inPack = inputInPack.getValue()
            val years = inputYears.getValue().toFloat()
            val price = inputPrice.getValue().toFloat()

            val user = User(uid = 0, perDay = perDay, inPack = inPack, years = years, price = price, currency = "EUR")

            viewModel.setUserData(user)
            viewModel.setState(SettingsViewModel.State.Done)

            finish()
        }

        viewModel = ViewModelProviders.of(this).get(SettingsViewModel::class.java)
        viewModel.state.observe(this, stateObserver)
        viewModel.user.observe(this, userObserver)
    }

    private fun onUserDataChanged(user: User) {
        inputPerDay.setValue(user.perDay)
        inputInPack.setValue(user.inPack)
        inputYears.setValue(user.years?.toInt())
        inputPrice.setValue(user.price?.toInt())
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