package nl.entreco.giddyapp.signup.link

import android.content.Intent
import nl.entreco.giddyapp.libauth.Authenticator
import nl.entreco.giddyapp.libauth.account.SignupResponse
import nl.entreco.giddyapp.libcore.onBg
import nl.entreco.giddyapp.libcore.onUi
import javax.inject.Inject

class LinkAccountUsecase @Inject constructor(
    private val authenticator: Authenticator
) {

    init {
        onBg {
            authenticator.clearAllObservers()
        }
    }

    fun exec(request: LinkAccountRequest, done:(LinkAccountResponse)->Unit){
        onBg {
            authenticator.link(request.context, request.resultCode, request.data) { response ->
                val result = when (response) {
                    is SignupResponse.Success -> LinkAccountResponse(true)
                    else -> LinkAccountResponse(false)
                }
                onUi { done(result) }
            }
        }
    }

    fun signinIntent(): Intent? = authenticator.signinIntent()
}