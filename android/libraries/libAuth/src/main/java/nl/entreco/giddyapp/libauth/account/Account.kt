package nl.entreco.giddyapp.libauth.account

import android.net.Uri

sealed class Account {
    data class Anomymous(val uid: String, val name: String) : Account()
    data class Authenticated(val uid: String, val name: String, val email: String, val url: Uri?) : Account()
    data class Error(val msg: String) : Account()
}