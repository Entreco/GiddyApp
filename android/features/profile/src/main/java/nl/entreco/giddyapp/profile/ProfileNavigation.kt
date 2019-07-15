package nl.entreco.giddyapp.profile

import android.content.Intent
import android.widget.Toast
import nl.entreco.giddyapp.libauth.Authenticator
import nl.entreco.giddyapp.libauth.user.User
import nl.entreco.giddyapp.libcore.di.AppScope
import nl.entreco.giddyapp.libcore.launch.DynamicLauncher
import nl.entreco.giddyapp.libcore.launch.features.CreatorNavigator
import nl.entreco.giddyapp.profile.loading.ProfileLoadingFragment
import nl.entreco.giddyapp.profile.profile.Profile
import nl.entreco.giddyapp.profile.profile.ProfileItem
import nl.entreco.giddyapp.profile.profile.header.ProfileHeaderAnonymousFrament
import nl.entreco.giddyapp.profile.profile.header.ProfileHeaderFragment
import nl.entreco.giddyapp.signup.SignupActivity
import javax.inject.Inject

class ProfileNavigation @Inject constructor(
    private val activity: ProfileActivity,
    @AppScope private val dynamicLauncher: DynamicLauncher,
    private val auth: Authenticator
) {
    private val fm = activity.supportFragmentManager

    private fun launchCreator() {
        CreatorNavigator.launch(activity) { progress, intent ->
            intent?.let {
                activity.startActivity(intent)
                activity.finish()
            }
        }
    }

    private fun launchSettings() {
//        LaunchHelper.launchSettings(activity, null)
//        activity.finish()
    }

    fun onStateChanged(state: Profile?) {
        when (state?.user) {
            is User.Anomymous -> showAnonymous()
            is User.Authenticated -> showLoggedIn()
            is User.Error -> showError()
            else -> showLoading()
        }
    }

    private fun showLoading() {
        fm.beginTransaction()
            .replace(R.id.profile_container, ProfileLoadingFragment())
            .commit()
    }

    private fun showAnonymous() {
        fm.beginTransaction()
            .replace(R.id.profile_container, ProfileHeaderAnonymousFrament())
            .commit()
    }

    private fun showLoggedIn() {
        fm.beginTransaction()
            .replace(R.id.profile_container, ProfileHeaderFragment())
            .commit()
    }

    private fun showError() {
        fm.beginTransaction()
            .replace(R.id.profile_container, ProfileHeaderFragment())
            .commit()
    }

    fun onProfileItemClicked(item: ProfileItem?) {
        when (item) {
            is ProfileItem.Creator -> launchCreator()
            is ProfileItem.Settings -> launchSettings()
            else -> Toast.makeText(activity, "NOT IMPLEMENTED: $item", Toast.LENGTH_SHORT).show()
        }
    }

    fun onSignup() {
        activity.startActivity(Intent(activity, SignupActivity::class.java))
    }

    fun onLogout(){
        auth.logout(activity){
            Toast.makeText(activity, "LOGGED OUT", Toast.LENGTH_SHORT).show()
            activity.finish()
        }
    }
}