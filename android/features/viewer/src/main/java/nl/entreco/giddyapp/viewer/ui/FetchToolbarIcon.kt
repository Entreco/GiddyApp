package nl.entreco.giddyapp.viewer.ui

import android.content.Context
import android.net.Uri
import nl.entreco.giddyapp.libauth.Authenticator
import nl.entreco.giddyapp.libauth.account.Account
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
            authenticator.signinOrAnonymous(context)
            authenticator.observe("toolbar") { user ->
                val icon = when (user) {
                    is Account.Authenticated -> user.url
                    else -> null
                }
                onUi { done(icon) }
            }
        }
    }

    fun clear() {
        onBg {
            authenticator.stopObserving("toolbar")
        }
    }
}