package nl.entreco.giddyapp.libauth.user

sealed class DeleteResponse{
    data class Ok(val uuid: String) : DeleteResponse()
    data class Err(val msg: String) : DeleteResponse()
}