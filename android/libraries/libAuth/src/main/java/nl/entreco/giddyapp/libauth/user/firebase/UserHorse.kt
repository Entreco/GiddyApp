package nl.entreco.giddyapp.libauth.user.firebase

sealed class UserHorse{
    data class Ok(val userId: String, val horseId: String) : UserHorse()
    data class Err(val msg: String) : UserHorse()
}