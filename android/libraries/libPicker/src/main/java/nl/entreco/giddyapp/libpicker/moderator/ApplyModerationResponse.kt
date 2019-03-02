package nl.entreco.giddyapp.libpicker.moderator

data class ApplyModerationResponse(val mod: Moderation)

sealed class Moderation {
    object Allowed : Moderation()
    data class Rejected(val contains: String = "", val missing: String = "") : Moderation()
}