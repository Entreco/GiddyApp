package nl.entreco.giddyapp.profile

import android.content.Intent
import android.widget.Toast
import nl.entreco.giddyapp.libauth.Authenticator
import nl.entreco.giddyapp.libauth.account.Account
import nl.entreco.giddyapp.libcore.launch.LaunchHelper
import nl.entreco.giddyapp.libcore.launch.features.CreatorNavigator
import nl.entreco.giddyapp.profile.header.ProfileHeaderAnonymousFrament
import nl.entreco.giddyapp.profile.header.ProfileHeaderFragment
import nl.entreco.giddyapp.profile.header.ProfileHeaderLoadingFragment
import nl.entreco.giddyapp.profile.matches.MatchFragment
import nl.entreco.giddyapp.profile.profile.Profile
import nl.entreco.giddyapp.profile.profile.ProfileFragment
import nl.entreco.giddyapp.profile.profile.ProfileItem
import nl.entreco.giddyapp.signup.SignupActivity
import javax.inject.Inject

class ProfileNavigation @Inject constructor(
    private val activity: ProfileActivity,
    private val auth: Authenticator
) {
    private val fm = activity.supportFragmentManager

    private fun launchCreator(item: ProfileItem) {
        CreatorNavigator.launch(activity) { progress, max, launch ->
            item.progress.set(progress.toInt())
            item.max.set(max.toInt())
            if (launch) {
                LaunchHelper.launchCreator(activity)
                activity.finish()
            }
        }
    }

    private fun showMatches() {
        fm.beginTransaction()
            .replace(R.id.profile_container, MatchFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun showProfile() {
        fm.beginTransaction()
            .replace(R.id.profile_container, ProfileFragment())
            .commit()
    }

    private fun showSettings() {
//        LaunchHelper.launchSettings(activity, null)
//        activity.finish()
    }

    private fun showAbout() {
//        LaunchHelper.launchAbout(activity, null)
//        activity.finish()
    }

    fun onStateChanged(state: Profile?) {
        when (state?.account) {
            is Account.Anomymous -> showAnonymous()
            is Account.Authenticated -> showLoggedIn()
            is Account.Error -> showError()
            else -> showLoading()
        }

        showProfile()
    }

    private fun showLoading() {
        fm.beginTransaction()
            .replace(R.id.header_container, ProfileHeaderLoadingFragment())
            .commit()
    }

    private fun showAnonymous() {
        fm.beginTransaction()
            .replace(R.id.header_container, ProfileHeaderAnonymousFrament())
            .commit()
    }

    private fun showLoggedIn() {
        fm.beginTransaction()
            .replace(R.id.header_container, ProfileHeaderFragment())
            .commit()
    }

    private fun showError() {
        fm.beginTransaction()
            .replace(R.id.header_container, ProfileHeaderFragment())
            .commit()
    }

    fun onProfileItemClicked(item: ProfileItem?) {
        when (item) {
            is ProfileItem.Upload -> launchCreator(item)
            is ProfileItem.Matches -> showMatches()
            is ProfileItem.Settings -> showSettings()
            is ProfileItem.About -> showAbout()
            else -> Toast.makeText(activity, "NOT IMPLEMENTED: $item", Toast.LENGTH_SHORT).show()
        }
    }

    fun onSignup() {
        activity.startActivity(Intent(activity, SignupActivity::class.java))
    }

    fun onLogout() {
        showLoading()
        auth.logout(activity) {
            Toast.makeText(activity, "LOGGED OUT", Toast.LENGTH_SHORT).show()
            activity.finish()
        }
    }
}