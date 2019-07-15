package nl.entreco.giddyapp.signup

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import nl.entreco.giddyapp.libauth.Authenticator
import nl.entreco.giddyapp.libauth.account.SignupResponse
import javax.inject.Inject

class SignupViewModel @Inject constructor(
    private val authenticator: Authenticator
) : ViewModel() {

    init {
        authenticator.clearAllObservers()
    }

    fun intent(): Intent? = authenticator.signinIntent()

    fun handleResult(context: Context, resultCode: Int, data: Intent?, done: (SignupResponse) -> Unit) {
        authenticator.link(context, resultCode, data) { response ->
            when (response) {
                is SignupResponse.Success -> cleanUp(response.uuid, response.oldUuid)
                else -> { /* FML */
                }
            }
            done(response)
        }
    }

    private fun cleanUp(uuid: String, oldUuid: String?) {
        oldUuid?.let { remove ->
            Log.i("CLEAN", "1) Copy data from users/$oldUuid to users/$uuid ")
            Log.i("CLEAN", "2) Delete user $oldUuid ")
        }
    }
}