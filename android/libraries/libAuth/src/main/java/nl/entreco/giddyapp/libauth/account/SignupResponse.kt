package nl.entreco.giddyapp.libauth.account

sealed class SignupResponse(){
    data class Success(val uuid: String, val oldUuid: String? = null) : SignupResponse()
    data class Failed(val error: String, val code: Int) : SignupResponse()
}