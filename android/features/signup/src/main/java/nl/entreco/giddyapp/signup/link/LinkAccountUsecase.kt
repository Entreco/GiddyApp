package nl.entreco.giddyapp.signup.link

import android.content.Intent
import nl.entreco.giddyapp.libauth.Authenticator
import nl.entreco.giddyapp.libauth.account.SignupResponse
import nl.entreco.giddyapp.libauth.account.firebase.FbAuthUiSettings
import nl.entreco.giddyapp.libauth.user.User
import nl.entreco.giddyapp.libcore.onBg
import nl.entreco.giddyapp.libcore.onUi
import nl.entreco.giddyapp.signup.SignupActivity
import javax.inject.Inject

class LinkAccountUsecase @Inject constructor(
    private val authenticator: Authenticator
) {

    fun exec(request: LinkAccountRequest, done: (LinkAccountResponse) -> Unit) {
        onBg {
            authenticator.link(request.context, request.resultCode, request.data) { response ->
                val result = when (response) {
                    is SignupResponse.Migrate -> continueWithMigration(response)
                    is SignupResponse.Success -> LinkAccountResponse.Success
                    is SignupResponse.Failed -> LinkAccountResponse.Failed(response.error)
                    else -> LinkAccountResponse.Failed("Unknown error")
                }
                onUi { done(result) }
            }
        }
    }

    private fun continueWithMigration(response: SignupResponse.Migrate): LinkAccountResponse {
        return when (val old = response.oldUser) {
            is User.Valid -> {
                // TODO entreco - 2019-07-18: Update all matches where we have OLD UID -> update to NEW UID
                old.uid
                LinkAccountResponse.Success
            }
            is User.Error -> LinkAccountResponse.Success
        }
    }

    fun signinIntent(settings: FbAuthUiSettings, link: String?): Intent? = authenticator.signinIntent(settings, link)
    fun canHandle(intent: Intent, done: (String) -> Unit) = authenticator.canHandle(intent, done)
}