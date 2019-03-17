package nl.entreco.giddyapp.viewer

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.swipeRight
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * ## Running instrumentation tests

In order to run instrumentation tests, parallel builds have to be turned off at the moment.
This means you can run them via `./gradlew connectedAndroidTest --no-parallel`.

Tooling support for this is being worked on – currently it's not possible to run
instrumentation tests for dynamic-feature modules from Android Studio directly.
Use the command line instead.
 */
@RunWith(AndroidJUnit4::class)
class ViewerActivityTest {

    @Rule
    @JvmField
    var activityRule: ActivityScenarioRule<ViewerActivity> =
        ActivityScenarioRule<ViewerActivity>(ViewerActivity::class.java)

    @Before
    fun setUp() {
//        mockBuilder = InstrumentationRegistry.getInstrumentation().asMockRunner().resetMocks()
    }

    @Test
    fun changeText_sameActivity() {

        // Type text and then press the button.
        onView(withId(R.id.currentView)).perform(swipeRight())
        onView(withId(R.id.currentView)).perform(swipeRight())
        onView(withId(R.id.currentView)).perform(swipeRight())
        onView(withId(R.id.currentView)).perform(swipeRight())
        onView(withId(R.id.currentView)).perform(swipeRight())
        onView(withId(R.id.currentView)).perform(swipeRight())
        onView(withId(R.id.currentView)).perform(swipeRight())
        onView(withId(R.id.currentView)).perform(swipeRight())

        // Check that the text was changed.
        onView(withId(R.id.fab)).check(matches(isDisplayed()))
    }
}