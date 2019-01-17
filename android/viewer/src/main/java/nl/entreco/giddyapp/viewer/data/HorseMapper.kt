package nl.entreco.giddyapp.viewer.data

import nl.entreco.giddyapp.viewer.Horse

internal class HorseMapper {
    fun map(fbHorse: FbHorse, imageRef: String): Horse {
        return Horse(
            fbHorse.name,
            fbHorse.startColor,
            fbHorse.endColor,
            imageRef,
            fbHorse.ext,
            fbHorse.posted.time
        )
    }
}