package nl.entreco.giddyapp.profile.profile

import android.content.Context
import nl.entreco.giddyapp.libauth.Authenticator
import nl.entreco.giddyapp.libcore.di.AppContext
import nl.entreco.giddyapp.libcore.onBg
import nl.entreco.giddyapp.libcore.onUi
import javax.inject.Inject

class FetchProfileUsecase @Inject constructor(
    @AppContext private val context: Context,
    private val authenticator: Authenticator
) {

    fun go(done: (Profile) -> Unit) {
        onBg {
            authenticator.silent(context)
            authenticator.observe { user ->
                onUi { done(Profile(user)) }
            }
        }
    }
}