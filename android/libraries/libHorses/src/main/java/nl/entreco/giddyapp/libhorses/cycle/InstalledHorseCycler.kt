package nl.entreco.giddyapp.libhorses.cycle

import nl.entreco.giddyapp.libhorses.Horse
import nl.entreco.giddyapp.libhorses.fetch.FetchHorseRequest
import nl.entreco.giddyapp.libhorses.fetch.FetchHorseUsecase

internal class InstalledHorseCycler(
    private val fetchHorseUsecase: FetchHorseUsecase
) : HorseCycler {
    override fun initialHorses(collection: List<Horse>): List<Horse> {
        return when {
            collection.isEmpty() -> listOf(Horse.None, Horse.None)
            collection.size < 2 -> listOf(collection[0], Horse.None)
            else -> collection
        }
    }

    override fun recycle(done: (List<Horse>) -> Unit) {
        fetchHorseUsecase.go(FetchHorseRequest()) { response ->
            done(response.horses)
        }
    }
}