package nl.entreco.giddyapp.creator.create

import android.net.Uri
import nl.entreco.giddyapp.libcore.HexString
import nl.entreco.giddyapp.libhorses.HorseCategory
import nl.entreco.giddyapp.libhorses.HorseGender
import nl.entreco.giddyapp.libhorses.HorseLevel
import nl.entreco.giddyapp.libhorses.HorsePrice

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