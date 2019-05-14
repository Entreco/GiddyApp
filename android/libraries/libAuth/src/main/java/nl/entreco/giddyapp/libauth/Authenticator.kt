package nl.entreco.giddyapp.libauth

import nl.entreco.giddyapp.libauth.user.SigninMethod
import nl.entreco.giddyapp.libauth.user.User

interface Authenticator {
    fun current(done: (User) -> Unit)
    fun link(provider: SigninMethod, done: (User) -> Unit)
    fun logout()
}