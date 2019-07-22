package nl.entreco.giddyapp.libauth.account

import nl.entreco.giddyapp.libauth.user.User

sealed class SignupResponse {
    data class Success(val name: String) : SignupResponse()
    data class Migrate(val uid: String, val oldUser: User) : SignupResponse()
    data class Failed(val error: String, val code: Int) : SignupResponse()
}