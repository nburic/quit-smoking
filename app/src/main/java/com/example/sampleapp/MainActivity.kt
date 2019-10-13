package com.example.sampleapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.example.sampleapp.util.Constants.DATA_SET_CODE
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var bottomNav: BottomNavigationView

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("!!!", "onCreate MainActivity")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        setupBottomNavMenu(findNavController(R.id.nav_host_fragment))

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        when (viewModel.inputDataNotSet()) {
            true -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivityForResult(intent, DATA_SET_CODE)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_actions_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController(R.id.nav_host_fragment)) || super.onOptionsItemSelected(item)
    }

    private fun setupBottomNavMenu(navController: NavController) {
        bottomNav = findViewById(R.id.nav_main)
        bottomNav.setupWithNavController(navController)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            DATA_SET_CODE -> {
                when (resultCode == Activity.RESULT_OK) {
                    true -> {
                        Toast.makeText(this, "[Data set successfully!]", Toast.LENGTH_SHORT).show()
                    }
                    false -> {
                        Toast.makeText(this, "[Data not set!]", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }
        }
    }
}
