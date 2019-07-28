package nl.entreco.giddyapp.libhorses.cycle

import nl.entreco.giddyapp.libhorses.Horse

interface HorseCycler {
    fun initialHorses(collection: List<Horse>): List<Horse>
    fun recycle(done:(List<Horse>)->Unit)
}