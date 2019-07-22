package nl.entreco.giddyapp.profile.profile

import nl.entreco.giddyapp.libauth.Authenticator
import nl.entreco.giddyapp.libcore.onBg
import javax.inject.Inject

class FetchProfileUsecase @Inject constructor(
    private val authenticator: Authenticator
) {

    fun go(key: String, done: (Profile) -> Unit) {
        onBg {
            authenticator.observe(key) { user ->
                done(Profile(user))
            }
        }
    }

    fun clear(key: String) {
        onBg {
            authenticator.stopObserving(key)
        }
    }
}