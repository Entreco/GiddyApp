package nl.entreco.giddyapp.libauth.user

sealed class User{
    data class Valid(val name: String,
                     val likes: List<UserLike>,
                     val dislikes: List<UserLike>) : User()
    data class Error(val msg: String) : User()
}