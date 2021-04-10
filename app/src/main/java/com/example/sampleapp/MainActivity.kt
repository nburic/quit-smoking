package com.example.sampleapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.sampleapp.data.db.user.UserEntity
import com.example.sampleapp.databinding.ActivityMainBinding
import com.example.sampleapp.util.hide
import com.example.sampleapp.util.show
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel>()

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.nav_host_fragment)

        setSupportActionBar(binding.toolbar)
        setupBottomNavMenu(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            Timber.d("Current destination ${destination.label}")

            val menu = binding.toolbar.menu

            when (destination.id) {
                R.id.settingsFragment -> {
                    menu.clear()
                    binding.bottomNav.hide()
                }
                else -> {
                    if (menu.children.count() == 0) {
                        menuInflater.inflate(R.menu.menu_toolbar_actions_main, menu)
                    }
                    binding.bottomNav.show()
                }
            }
        }

        viewModel.user.observe(this, { user: UserEntity? ->
            Timber.d("User observe activity $user")

            when (user) {
                null -> {
                    navController.navigate(NavGraphDirections.actionGlobalInclusiveSettingsFragment())
                }
                else -> {
                    if (navController.currentDestination?.id == R.id.settingsFragment) {
                        navController.navigate(NavGraphDirections.actionGlobalProgressFragment())
                    }
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_actions_main, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)

        return when (item.itemId) {
            R.id.menu_item_settings -> {
                navController.navigate(NavGraphDirections.actionGlobalSettingsFragment())
                true
            }
            R.id.menu_item_inclusive -> {
                navController.navigate(NavGraphDirections.actionGlobalInclusiveSettingsFragment())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupBottomNavMenu(navController: NavController) {
        binding.bottomNav.setupWithNavController(navController)
    }
}
