package nl.entreco.giddyapp.libauth.user

data class User(
    val name: String,
    val likes: List<String>,
    val dislikes: List<String>)