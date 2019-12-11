package com.example.sampleapp

import android.app.Activity
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.example.sampleapp.db.User
import com.example.sampleapp.services.NotificationJobService
import com.example.sampleapp.util.Constants.DATA_SET_CODE
import com.google.android.material.bottomnavigation.BottomNavigationView
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var bottomNav: BottomNavigationView

    private lateinit var viewModel: MainViewModel

    private lateinit var jobScheduler: JobScheduler

    private val userObserver = Observer { user: User? ->
        when (user == null) {
            true -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivityForResult(intent, DATA_SET_CODE)
            }
            false -> onUserChanged(user)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        setupBottomNavMenu(findNavController(R.id.nav_host_fragment))

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.user.observe(this, userObserver)

        jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
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

                        /**
                         * Schedule job to notify when the goal is achieved
                         */
                        jobScheduler.cancel(MainApplication.GOAL_JOB_ID)
                    }
                    false -> {
                        Toast.makeText(this, "[Data not set!]", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }
        }
    }

    private fun onUserChanged(user: User) {
        user.goal?.let {
            if (it > System.currentTimeMillis()) {
                scheduleJob(it)
            }
        }
    }

    private fun scheduleJob(timeInMillis: Long) {
        val serviceName = ComponentName(packageName, NotificationJobService::class.java.name)

        val builder = JobInfo.Builder(MainApplication.GOAL_JOB_ID, serviceName)
            .setMinimumLatency(timeInMillis)
            .setPersisted(true)

        val jobInfo = builder.build()
        jobScheduler.schedule(jobInfo)

        Timber.d("!!! job scheduled")
    }
}
