package nl.entreco.giddyapp.libauth.user

import android.net.Uri

sealed class User {
    data class Anomymous(val uid: String) : User()
    data class Authenticated(val uid: String, val name: String, val url: Uri?) : User()
    data class Error(val msg: String) : User()
}