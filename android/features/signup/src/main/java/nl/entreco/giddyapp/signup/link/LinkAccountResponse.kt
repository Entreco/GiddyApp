package nl.entreco.giddyapp.signup.link

sealed class LinkAccountResponse{
    object Success : LinkAccountResponse()
    object Cancelled : LinkAccountResponse()
    data class Failed(val msg: String) : LinkAccountResponse()
}