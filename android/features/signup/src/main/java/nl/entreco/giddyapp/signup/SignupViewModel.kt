package nl.entreco.giddyapp.signup

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import nl.entreco.giddyapp.signup.link.LinkAccountRequest
import nl.entreco.giddyapp.signup.link.LinkAccountResponse
import nl.entreco.giddyapp.signup.link.LinkAccountUsecase
import javax.inject.Inject

class SignupViewModel @Inject constructor(
    private val linkAccountUsecase: LinkAccountUsecase
) : ViewModel() {

    fun intent(): Intent? = linkAccountUsecase.signinIntent()

    fun handleResult(context: Context, resultCode: Int, data: Intent?, done: (LinkAccountResponse) -> Unit) {
        linkAccountUsecase.exec(LinkAccountRequest(context, resultCode, data)) { response ->
            done(response)
        }
    }
}