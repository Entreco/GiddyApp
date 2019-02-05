package nl.entreco.giddyapp.libs.horses.create

import android.net.Uri
import nl.entreco.giddyapp.core.HexString
import nl.entreco.giddyapp.libs.horses.HorseGender

data class CreateHorseRequest(
    val name: String,
    val description: String,
    val gender: HorseGender,
    val image: Uri,
    val startColor: nl.entreco.giddyapp.core.HexString,
    val endColor: nl.entreco.giddyapp.core.HexString
)