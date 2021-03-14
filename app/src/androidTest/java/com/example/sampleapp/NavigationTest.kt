package com.example.sampleapp

import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
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
}