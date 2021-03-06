package nl.entreco.giddyapp.profile.header

import android.content.Intent
import nl.entreco.giddyapp.profile.ProfileActivity
import nl.entreco.giddyapp.signup.SignupActivity
import javax.inject.Inject

class ProfileHeaderNavigation @Inject constructor(
    private val activity: ProfileActivity
) {
    fun onSignup() {
        val launch = Intent(activity, SignupActivity::class.java)
        activity.startActivityForResult(launch, SignupActivity.RC_SIGN_IN)
    }
}