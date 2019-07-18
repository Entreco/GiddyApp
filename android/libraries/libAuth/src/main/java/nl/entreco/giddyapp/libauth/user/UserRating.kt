package nl.entreco.giddyapp.libauth.user

sealed class UserRating {
    object Ok : UserRating()
    object Err : UserRating()
}