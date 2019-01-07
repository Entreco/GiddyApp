package nl.entreco.giddyapp.viewer.data

import nl.entreco.giddyapp.viewer.Horse

internal class HorseMapper {
    fun map(fbHorse: FbHorse, imageRef: String): Horse? {
        val name = fbHorse.name
        val start = fbHorse.startColor
        val end = fbHorse.endColor
        return Horse(name, start, end, imageRef)
    }
}