package nl.entreco.giddyapp.libhorses

sealed class HorseRating {
    object Ok : HorseRating()
    data class Failed(val msg: String) : HorseRating()
}