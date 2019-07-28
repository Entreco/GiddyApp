package nl.entreco.giddyapp.libauth.user

sealed class User {
    data class Valid(
        val uid: String,
        val name: String
    ) : User()

    data class Anonymous(val uid: String) : User()
    data class Error(val msg: String) : User()
}