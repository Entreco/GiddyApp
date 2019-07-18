package nl.entreco.giddyapp.libauth.account

sealed class SignupResponse(){
    data class Success(val name: String) : SignupResponse()
    data class Failed(val error: String, val code: Int) : SignupResponse()
}