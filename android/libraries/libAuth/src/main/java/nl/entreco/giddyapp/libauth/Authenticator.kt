package nl.entreco.giddyapp.libauth

import android.content.Context
import android.content.Intent
import androidx.annotation.DrawableRes
import nl.entreco.giddyapp.libauth.account.SignupResponse
import nl.entreco.giddyapp.libauth.account.Account

interface Authenticator {
    fun signinOrAnonymous(context: Context)
    fun signinIntent(@DrawableRes logo: Int) : Intent
    fun link(context: Context, resultCode: Int, data: Intent?, done : (SignupResponse)->Unit)
    fun logout(context: Context, done:()->Unit)
    fun delete(uuid: String)

    fun observe(key: String, done: (Account)->Unit)
    fun stopObserving(key: String)
}