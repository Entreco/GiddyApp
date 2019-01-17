package nl.entreco.giddyapp.viewer.data

import nl.entreco.giddyapp.viewer.domain.Horse
import nl.entreco.giddyapp.viewer.domain.HorseDetail

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
        return HorseDetail(fbHorse.name, if (fbHorse.description.isBlank()) "no description" else fbHorse.description)
    }
}