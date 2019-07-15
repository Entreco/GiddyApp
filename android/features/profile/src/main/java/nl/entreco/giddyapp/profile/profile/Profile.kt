package nl.entreco.giddyapp.profile.profile

import nl.entreco.giddyapp.libauth.user.User
import nl.entreco.giddyapp.profile.R

data class Profile(val user: User) {
    fun name(): String = user.toString()
    fun desc(): Int = when(user){
        is User.Anomymous -> R.string.profile_anonymous
        is User.Authenticated -> R.string.profile_authenticated
        else -> R.string.profile_error
    }
}