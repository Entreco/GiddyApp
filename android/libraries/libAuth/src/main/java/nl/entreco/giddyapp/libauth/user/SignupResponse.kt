package nl.entreco.giddyapp.libauth.user

sealed class SignupResponse(){
    data class Success(val id: String) : SignupResponse()
    data class Failed(val error: String, val code: Int) : SignupResponse()
}