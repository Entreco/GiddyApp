package nl.entreco.giddyapp.viewer.ui

import android.content.Context
import android.net.Uri
import nl.entreco.giddyapp.featureComponent
import nl.entreco.giddyapp.libauth.Authenticator
import nl.entreco.giddyapp.libauth.user.User
import nl.entreco.giddyapp.libcore.R
import nl.entreco.giddyapp.libcore.di.AppContext
import nl.entreco.giddyapp.libcore.onBg
import nl.entreco.giddyapp.libcore.onUi
import javax.inject.Inject

class FetchToolbarIcon @Inject constructor(
    @AppContext private val context: Context,
    private val authenticator: Authenticator
) {

    fun go(done: (Uri?) -> Unit) {
        onBg {
            authenticator.silent(context)
            authenticator.observe { user ->
                val icon = when (user) {
                    is User.Authenticated -> user.url
                    else -> Uri.parse("android.resource://nl.entreco.giddyapp/${R.drawable.ic_profile}")
                }
                onUi { done(icon) }
            }
        }
    }
}