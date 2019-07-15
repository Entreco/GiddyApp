package nl.entreco.giddyapp.profile.profile

import android.net.Uri
import nl.entreco.giddyapp.libauth.user.User
import nl.entreco.giddyapp.profile.R

data class Profile(val user: User) {

    fun name(): String = when(user){
        is User.Anomymous -> "Profile"
        is User.Authenticated -> user.name
        else -> ""
    }

    fun image(): Uri? = when(user){
        is User.Authenticated -> user.url
        else -> Uri.parse("android.resource://nl.entreco.giddyapp/${R.drawable.ic_face_black_24dp}")
    }

    fun desc(): String = when(user){
        is User.Anomymous -> user.uid
        is User.Authenticated -> user.email
        else -> ""
    }


    fun help(): Int = when(user){
        is User.Anomymous -> R.string.profile_anonymous
        is User.Authenticated -> R.string.profile_authenticated
        else -> R.string.profile_error
    }
}