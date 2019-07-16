package nl.entreco.giddyapp.viewer.ratings

data class RateHorseRequest(
    val horseId: String,
    val horseName: String,
    val horseRef: String,
    val like: Boolean
)