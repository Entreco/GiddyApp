package nl.entreco.giddyapp.profile.header

import android.content.Intent
import nl.entreco.giddyapp.profile.ProfileActivity
import nl.entreco.giddyapp.signup.SignupActivity
import javax.inject.Inject

class ProfileHeaderNavigation @Inject constructor(
    private val activity: ProfileActivity
) {
    fun onSignup() {
        activity.startActivity(Intent(activity, SignupActivity::class.java))
    }
}