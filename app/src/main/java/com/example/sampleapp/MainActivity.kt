package com.example.sampleapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.example.sampleapp.data.db.user.UserEntity
import com.example.sampleapp.databinding.ActivityMainBinding
import com.example.sampleapp.util.hide
import com.example.sampleapp.util.show
import timber.log.Timber


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel>()

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.nav_host_fragment)

        setupBottomNavMenu(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            Timber.d("Current destination ${destination.label}")

            val menuItem: MenuItem = binding.toolbar.menu.findItem(R.id.menu_item_settings)
            binding.toolbar.title = destination.label.toString()

            when (destination.id) {
                R.id.settingsFragment -> {
                    menuItem.isVisible = false
                    binding.bottomNav.hide()
                }
                else -> {
                    menuItem.setOnMenuItemClickListener {
                        navController.navigate(NavGraphDirections.actionGlobalSettingsFragment())
                        true
                    }
                    menuItem.isVisible = true
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_actions_main, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)

        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    private fun setupBottomNavMenu(navController: NavController) {
        binding.bottomNav.setupWithNavController(navController)
    }
}
