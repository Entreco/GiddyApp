package nl.entreco.giddyapp.libauth.user

sealed class User
data class AnomymousUser(val uid: String) : User()
data class AuthenticatedUser(val uid: String) : User()
data class ErrorUser(val msg: String) : User()