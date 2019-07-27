package nl.entreco.giddyapp.libhorses.cycle

import nl.entreco.giddyapp.libhorses.Horse
import kotlin.math.min

internal class InstantHorseCycler : HorseCycler {

    companion object {
        private const val MAX_HORSES = 5
    }

    override fun initialHorses(collection: List<Horse>): List<Horse> {
        return when {
            collection.isEmpty() -> listOf(Horse.None, Horse.Install)
            collection.size < 2 -> listOf(collection[0], Horse.Install)
            else -> {
                collection.subList(0, min(collection.size, MAX_HORSES)).toMutableList().apply {
                    add(Horse.Install)
                }
            }
        }
    }
}