package nl.entreco.giddyapp.libauth.user

sealed class User{
    data class Valid(val name: String,
                     val likes: List<String>,
                     val dislikes: List<String>) : User()
    data class Error(val msg: String) : User()
}