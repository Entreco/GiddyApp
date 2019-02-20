package nl.entreco.giddyapp.libs.horses.create

import android.net.Uri
import nl.entreco.giddyapp.core.HexString
import nl.entreco.giddyapp.libs.horses.HorseCategory
import nl.entreco.giddyapp.libs.horses.HorseGender
import nl.entreco.giddyapp.libs.horses.HorseLevel
import nl.entreco.giddyapp.libs.horses.HorsePrice

data class CreateHorseRequest(
    val name: String,
    val description: String,
    val gender: HorseGender,
    val price: HorsePrice,
    val category: HorseCategory,
    val level: HorseLevel,
    val image: Uri,
    val startColor: HexString,
    val endColor: HexString
)