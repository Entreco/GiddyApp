package nl.entreco.giddyapp.profile.profile

import android.net.Uri
import nl.entreco.giddyapp.libauth.account.Account
import nl.entreco.giddyapp.profile.R

data class Profile(val account: Account) {

    fun name(): String = when (account) {
        is Account.Authenticated -> account.name
        is Account.Anomymous -> ""
        else -> ""
    }

    fun image(): Uri? = when (account) {
        is Account.Authenticated -> account.url
        else -> Uri.parse("android.resource://nl.entreco.giddyapp/${R.drawable.ic_face_black_24dp}")
    }

    fun desc(): String = when (account) {
        is Account.Anomymous -> account.uid
        is Account.Authenticated -> account.email
        else -> ""
    }
}