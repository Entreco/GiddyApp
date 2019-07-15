package nl.entreco.giddyapp.libauth

import android.content.Context
import android.content.Intent
import nl.entreco.giddyapp.libauth.user.SignupResponse
import nl.entreco.giddyapp.libauth.user.User

interface Authenticator {
    fun silent(context: Context)
    fun observe(key: String, done: (User)->Unit)
    fun stopObserving(key: String)
    fun upgrade() : Intent
    fun merge(resultCode: Int, data: Intent?, done : (SignupResponse)->Unit)
    fun logout(context: Context, done:()->Unit)
    fun clearAllObservers()
}