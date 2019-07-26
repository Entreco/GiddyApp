package nl.entreco.giddyapp.profile.profile

import nl.entreco.giddyapp.libauth.Authenticator
import nl.entreco.giddyapp.libcore.onBg
import nl.entreco.giddyapp.libcore.onUi
import nl.entreco.giddyapp.profile.profile.Profile
import javax.inject.Inject

class FetchProfileUsecase @Inject constructor(
    private val authenticator: Authenticator
) {

    fun go(done: (Profile) -> Unit) {
        clear()
        onBg {
            authenticator.observe("profile") { user ->
                onUi {
                    done(Profile(user))
                }
            }
        }
    }

    fun clear() {
        onBg {
            authenticator.stopObserving("profile")
        }
    }
}