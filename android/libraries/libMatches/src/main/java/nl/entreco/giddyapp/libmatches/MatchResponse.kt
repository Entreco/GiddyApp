package nl.entreco.giddyapp.libmatches

sealed class MatchResponse {
    object Ok : MatchResponse()
    data class Failed(val msg: String) : MatchResponse()
}