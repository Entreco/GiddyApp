package nl.entreco.giddyapp.profile.profile

import android.content.ContentResolver
import android.content.res.Resources
import android.net.Uri
import nl.entreco.giddyapp.libauth.account.Account
import nl.entreco.giddyapp.libcore.R

data class Profile(val account: Account) {

    fun uid(): String = when (account) {
        is Account.Authenticated -> account.uid
        is Account.Anomymous -> account.uid
        else -> ""
    }

    fun name(): String = when (account) {
        is Account.Authenticated -> account.name
        is Account.Anomymous -> ""
        else -> ""
    }

    fun image(resources: Resources): Uri? = when (account) {
        is Account.Authenticated -> account.url
        else -> Uri.Builder()
            .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
            .authority(resources.getResourcePackageName(R.drawable.ic_profile))
            .appendPath(resources.getResourceTypeName(R.drawable.ic_profile))
            .appendPath(resources.getResourceEntryName(R.drawable.ic_profile))
            .build()
    }

    fun desc(): String = when (account) {
        is Account.Anomymous -> account.uid
        is Account.Authenticated -> account.email
        else -> ""
    }
}