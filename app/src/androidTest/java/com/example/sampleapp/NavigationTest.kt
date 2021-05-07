package com.example.sampleapp

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.example.sampleapp.ui.home.HomeFragment
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class NavigationTest {

    @Test
    fun toolbarMenu_clickOnSettingsItem_OpensSettingsScreen() {
        val appContext = getInstrumentation().targetContext
        val activityScenario = launch(MainActivity::class.java)

        // Open the overflow menu OR open the options menu,
        // depending on if the device has a hardware or software overflow menu button.
        openActionBarOverflowOrOptionsMenu(appContext)

        // Click the item.
        onView(withText(R.string.menu_settings_title)).perform(click())

        // Check that tasks screen was opened.
        onView(withId(R.id.settings_layout)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        // When using ActivityScenario.launch, always call close()
        activityScenario.close()
    }

    // only if app has no user data (observing viewModel data)
    @Test
    fun testFirstRun() {
        val mockNavController = TestNavHostController(ApplicationProvider.getApplicationContext())

        runOnUiThread {
            mockNavController.setGraph(R.navigation.nav_graph)
        }

        val scenario = launchFragmentInContainer {
            HomeFragment().also { fragment ->
                fragment.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                    if (viewLifecycleOwner != null) {
                        Navigation.setViewNavController(fragment.requireView(), mockNavController)
                    }
                }
            }
        }

        scenario.onFragment {
            assertThat(mockNavController.currentDestination?.id).isEqualTo(R.id.settingsFragment)
        }
    }
}