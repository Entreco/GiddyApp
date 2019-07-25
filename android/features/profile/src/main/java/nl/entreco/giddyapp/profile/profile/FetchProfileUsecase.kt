package nl.entreco.giddyapp.profile.profile

import nl.entreco.giddyapp.libauth.Authenticator
import nl.entreco.giddyapp.libcore.onBg
import javax.inject.Inject

class FetchProfileUsecase @Inject constructor(
    private val authenticator: Authenticator
) {

    fun go(done: (Profile) -> Unit) {
        onBg {
            authenticator.observe("profile") { user ->
                done(Profile(user))
            }
        }
    }

    fun clear() {
        onBg {
            authenticator.stopObserving("profile")
        }
    }
}