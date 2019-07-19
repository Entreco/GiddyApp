package nl.entreco.giddyapp.signup

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import nl.entreco.giddyapp.libcore.toSingleEvent
import nl.entreco.giddyapp.signup.link.LinkAccountRequest
import nl.entreco.giddyapp.signup.link.LinkAccountResponse
import nl.entreco.giddyapp.signup.link.LinkAccountUsecase
import javax.inject.Inject

class SignupViewModel @Inject constructor(
    private val linkAccountUsecase: LinkAccountUsecase
) : ViewModel() {

    private val clicks = MutableLiveData<Intent>()
    fun clicks(): LiveData<Intent> = clicks.toSingleEvent()

    private fun intent(): Intent? =
        linkAccountUsecase.signinIntent(nl.entreco.giddyapp.libcore.R.drawable.ic_launcher_foreground)

    fun signUp() {
        clicks.postValue(intent())
    }

    fun handleResult(context: Context, resultCode: Int, data: Intent?, done: (LinkAccountResponse) -> Unit) {
        linkAccountUsecase.exec(LinkAccountRequest(context, resultCode, data)) { response ->
            done(response)
        }
    }
}