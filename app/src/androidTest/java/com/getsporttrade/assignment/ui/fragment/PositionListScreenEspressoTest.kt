package com.getsporttrade.assignment.ui.fragment

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.getsporttrade.assignment.MainActivity
import com.getsporttrade.assignment.R
import com.getsporttrade.assignment.repository.PositionRepository
import com.getsporttrade.assignment.ui.widget.PositionViewHolder
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class PositionListScreenEspressoTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var positionRepo: PositionRepository

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun verifyToolbarDisplaysAppName() {
        onView(withText(R.string.app_name)).check(matches(isDisplayed()))
    }

    @Test
    fun verifyListIsLoading() {
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))
    }

    @Test
    fun veifyListItemIsClickable() {
        onView(withId(R.id.recycler_view))
            .perform(actionOnItemAtPosition<PositionViewHolder>(0, click()))
    }
}