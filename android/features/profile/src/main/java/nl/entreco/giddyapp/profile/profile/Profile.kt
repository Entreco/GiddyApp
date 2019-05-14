package nl.entreco.giddyapp.profile.profile

import nl.entreco.giddyapp.libauth.user.User

data class Profile(val user: User) {
    fun name(): String = user.toString()
}