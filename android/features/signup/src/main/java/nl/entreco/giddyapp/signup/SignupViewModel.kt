package nl.entreco.giddyapp.signup

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import nl.entreco.giddyapp.libauth.account.firebase.FbAuthUiSettings
import nl.entreco.giddyapp.signup.link.LinkAccountRequest
import nl.entreco.giddyapp.signup.link.LinkAccountResponse
import nl.entreco.giddyapp.signup.link.LinkAccountUsecase
import javax.inject.Inject

class SignupViewModel @Inject constructor(
    private val linkAccountUsecase: LinkAccountUsecase
) : ViewModel() {

    private val uiSettings = FbAuthUiSettings(R.style.SignupTheme, R.layout.custom_auth_ui)
        .withEmail(R.id.signup_email)
        .withPhone(R.id.signup_phone)
        .withGoogle(R.id.signup_google)

    fun intent(link: String? = null): Intent? = linkAccountUsecase.signinIntent(uiSettings, link)

    fun handleResult(context: Context, resultCode: Int, data: Intent?, done: (LinkAccountResponse) -> Unit) {
        linkAccountUsecase.exec(LinkAccountRequest(context, resultCode, data)) { response ->
            done(response)
        }
    }

    fun canHandle(intent: Intent, done: (String) -> Unit) {
        linkAccountUsecase.canHandle(intent, done)
    }
}