package nl.entreco.giddyapp.libhorses.cycle

import nl.entreco.giddyapp.libhorses.Horse

internal class InstalledHorseCycler : HorseCycler {
    override fun initialHorses(collection: List<Horse>): List<Horse> {
        return when {
            collection.isEmpty() -> listOf(Horse.None, Horse.None)
            collection.size < 2 -> listOf(collection[0], Horse.None)
            else -> collection
        }
    }
}