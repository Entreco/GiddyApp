package nl.entreco.giddyapp.libauth.user

sealed class User {
    data class Anomymous(val uid: String) : User()
    data class Authenticated(val uid: String) : User()
    data class Error(val msg: String) : User()
}