package nl.entreco.giddyapp.core.horse.data

import nl.entreco.giddyapp.libs.horses.HexString
import nl.entreco.giddyapp.libs.horses.Horse
import nl.entreco.giddyapp.libs.horses.HorseDetail
import nl.entreco.giddyapp.libs.horses.HorseGender

internal class HorseMapper {
    fun map(fbHorse: FbHorse, imageRef: String): Horse {
        return Horse(
            HexString.from(fbHorse.startColor),
            HexString.from(fbHorse.endColor),
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

    fun create(name: String, description: String, gender: HorseGender, startColor: HexString, endColor: HexString): FbHorse {
        return FbHorse(
            name = name,
            description = description,
            gender = gender.number,
            startColor = startColor.hex(),
            endColor = endColor.hex()
        )
    }
}