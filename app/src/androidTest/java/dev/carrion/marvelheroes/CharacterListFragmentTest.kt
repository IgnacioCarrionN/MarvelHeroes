package dev.carrion.marvelheroes

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 *
 * Test for CharacterListFragment
 *
 * Remember to open Settings>Developer options on the emulator and turn the following options off:
 * - Window animation scale
 * - Transition animation scale
 * - Animation duration scale
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class CharacterListFragmentTest {

    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun onFirstLoadShowsLoadingDialog(){
        onView(withId(R.id.txtLoading))
            .check(matches(isDisplayed()))
    }

}