package nl.entreco.giddyapp.libs.horses.data

import nl.entreco.giddyapp.core.HexString
import nl.entreco.giddyapp.libs.horses.Horse
import nl.entreco.giddyapp.libs.horses.HorseDetail
import nl.entreco.giddyapp.libs.horses.HorseGender

internal class HorseMapper {
    fun map(fbHorse: FbHorse, imageRef: String): Horse {
        return Horse(
            nl.entreco.giddyapp.core.HexString.from(fbHorse.startColor),
            nl.entreco.giddyapp.core.HexString.from(fbHorse.endColor),
            imageRef,
            fbHorse.ext,
            fbHorse.posted.time,
            toDetails(fbHorse)
        )
    }

    private fun toDetails(fbHorse: FbHorse): HorseDetail {
        return HorseDetail(
            fbHorse.name,
            mapDescription(fbHorse),
            mapGender(fbHorse.gender)
        )
    }

    private fun mapDescription(fbHorse: FbHorse) =
        if (fbHorse.description.isBlank()) "no description" else fbHorse.description

    private fun mapGender(gender: Int): HorseGender {
        return when (gender) {
            0 -> HorseGender.Male
            1 -> HorseGender.Female
            2 -> HorseGender.Gelding
            else -> HorseGender.Unknown
        }
    }

    fun create(name: String, description: String, gender: HorseGender, startColor: nl.entreco.giddyapp.core.HexString, endColor: nl.entreco.giddyapp.core.HexString): FbHorse {
        return FbHorse(
            name = name,
            description = description,
            gender = gender.number,
            startColor = startColor.hex(),
            endColor = endColor.hex()
        )
    }
}