package nl.entreco.giddyapp.libs.horses.fetch

import nl.entreco.giddyapp.core.onBg
import nl.entreco.giddyapp.core.onUi
import nl.entreco.giddyapp.viewer.domain.HorseService
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
            service.fetch(ids) { response ->
                onUi { done(FetchHorseResponse(response)) }
            }
        }
    }
}