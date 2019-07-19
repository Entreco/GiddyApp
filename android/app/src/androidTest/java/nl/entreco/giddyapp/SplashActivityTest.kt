package nl.entreco.giddyapp

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SplashActivityTest {

    @Rule
    @JvmField
    var activityRule: ActivityTestRule<SplashActivity> = ActivityTestRule(SplashActivity::class.java, true, false)

    @Test
    fun itShouldBeCool() {
        activityRule.launchActivity(null)
        assert(true)
    }
}