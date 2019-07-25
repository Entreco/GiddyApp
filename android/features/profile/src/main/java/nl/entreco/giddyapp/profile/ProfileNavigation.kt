package nl.entreco.giddyapp.profile

import android.util.Log
import android.widget.Toast
import nl.entreco.giddyapp.libauth.Authenticator
import nl.entreco.giddyapp.libauth.account.Account
import nl.entreco.giddyapp.profile.header.ProfileHeaderAnonymousFrament
import nl.entreco.giddyapp.profile.header.ProfileHeaderFragment
import nl.entreco.giddyapp.profile.header.ProfileHeaderLoadingFragment
import nl.entreco.giddyapp.profile.menu.MenuFragment
import nl.entreco.giddyapp.profile.profile.Profile
import javax.inject.Inject

class ProfileNavigation @Inject constructor(
    private val activity: ProfileActivity,
    private val auth: Authenticator
) {
    private val fm = activity.supportFragmentManager

    init {
        fm.addOnBackStackChangedListener {
            Log.i("PROFILE", "PROFILE ProfileNavigation backstack: ${fm.backStackEntryCount}")
        }
    }

    fun onStateChanged(state: Profile?) {
        when (state?.account) {
            is Account.Anomymous -> showAnonymous()
            is Account.Authenticated -> showLoggedIn()
            is Account.Error -> showError()
            else -> showLoading()
        }
    }

    fun showProfile() {
        fm.beginTransaction()
            .replace(R.id.profile_container, MenuFragment(), "Profile")
            .commit()
    }

    private fun showLoading() {
        fm.beginTransaction()
            .replace(R.id.header_container, ProfileHeaderLoadingFragment(), "Header")
            .commit()
    }

    private fun showAnonymous() {
        fm.beginTransaction()
            .replace(R.id.header_container, ProfileHeaderAnonymousFrament(), "Header")
            .commit()
    }

    private fun showLoggedIn() {
        fm.beginTransaction()
            .replace(R.id.header_container, ProfileHeaderFragment(), "Header")
            .commit()
    }

    private fun showError() {
        fm.beginTransaction()
            .replace(R.id.header_container, ProfileHeaderFragment(), "Header")
            .commit()
    }

    fun onLogout() {
        showLoading()
        auth.logout(activity) {
            Toast.makeText(activity, "LOGGED OUT", Toast.LENGTH_SHORT).show()
            activity.finish()
        }
    }
}