package nl.entreco.giddyapp.libhorses.fetch

import nl.entreco.giddyapp.libhorses.HorseService
import nl.entreco.giddyapp.libcore.onBg
import nl.entreco.giddyapp.libcore.onUi
import javax.inject.Inject

class FetchHorseUsecase @Inject constructor(
    private val service: HorseService
) {

    companion object {
        private const val BATCH_SIZE = 10
        private const val RANDOM = ""
    }

    fun go(request: FetchHorseRequest, done: (FetchHorseResponse) -> Unit) {
        onBg {
            val ids = when {
                request.id.isNullOrBlank() -> (0 until BATCH_SIZE).map { RANDOM }
                else -> listOf(request.id) + (1 until BATCH_SIZE).map { RANDOM }
            }
            service.retrieve(ids, request.filterOptions) { response ->
                onUi { done(FetchHorseResponse(response)) }
            }
        }
    }
}