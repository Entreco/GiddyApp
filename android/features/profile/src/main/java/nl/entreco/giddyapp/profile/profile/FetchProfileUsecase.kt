package nl.entreco.giddyapp.profile.profile

import nl.entreco.giddyapp.libauth.Authenticator
import nl.entreco.giddyapp.libcore.onBg
import nl.entreco.giddyapp.libcore.onUi
import javax.inject.Inject

class FetchProfileUsecase @Inject constructor(
    private val authenticator: Authenticator
) {

    fun go(done: (Profile) -> Unit) {
        onBg {
            authenticator.current { user ->
                onUi { done(Profile(user)) }
            }
        }
    }
}