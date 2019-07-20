package nl.entreco.giddyapp.libauth

import android.content.Context
import android.content.Intent
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.annotation.StyleRes
import nl.entreco.giddyapp.libauth.account.SignupResponse
import nl.entreco.giddyapp.libauth.account.Account
import nl.entreco.giddyapp.libauth.account.firebase.FbAuthUiSettings

interface Authenticator {
    fun signinOrAnonymous(context: Context)
    fun signinIntent(settings: FbAuthUiSettings, link: String?) : Intent
    fun link(context: Context, resultCode: Int, data: Intent?, done : (SignupResponse)->Unit)
    fun logout(context: Context, done:()->Unit)
    fun delete(uuid: String)

    fun observe(key: String, done: (Account)->Unit)
    fun stopObserving(key: String)
    fun canHandle(intent: Intent, done: (String) -> Unit)
}