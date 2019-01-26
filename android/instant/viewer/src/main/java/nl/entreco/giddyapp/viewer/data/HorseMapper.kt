package nl.entreco.giddyapp.viewer.data

import nl.entreco.giddyapp.viewer.domain.Horse
import nl.entreco.giddyapp.viewer.domain.HorseDetail
import nl.entreco.giddyapp.viewer.domain.HorseGender

internal class HorseMapper {
    fun map(fbHorse: FbHorse, imageRef: String): Horse {
        return Horse(
            fbHorse.startColor,
            fbHorse.endColor,
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
}