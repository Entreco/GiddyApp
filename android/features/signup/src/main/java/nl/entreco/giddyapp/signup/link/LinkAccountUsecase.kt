package nl.entreco.giddyapp.signup.link

import android.content.Intent
import androidx.annotation.DrawableRes
import nl.entreco.giddyapp.libauth.Authenticator
import nl.entreco.giddyapp.libauth.account.SignupResponse
import nl.entreco.giddyapp.libcore.onBg
import nl.entreco.giddyapp.libcore.onUi
import javax.inject.Inject

class LinkAccountUsecase @Inject constructor(
    private val authenticator: Authenticator
) {

    fun exec(request: LinkAccountRequest, done: (LinkAccountResponse) -> Unit) {
        onBg {
            authenticator.link(request.context, request.resultCode, request.data) { response ->
                val result = when (response) {
                    is SignupResponse.Success -> LinkAccountResponse.Success
                    is SignupResponse.Failed -> LinkAccountResponse.Failed(response.error)
                    else -> LinkAccountResponse.Failed("Unknown error")
                }
                onUi { done(result) }
            }
        }
    }

    fun signinIntent(@DrawableRes logo: Int): Intent? = authenticator.signinIntent(logo)
}