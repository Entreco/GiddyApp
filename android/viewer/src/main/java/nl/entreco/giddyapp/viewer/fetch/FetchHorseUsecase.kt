package nl.entreco.giddyapp.viewer.fetch

import nl.entreco.giddyapp.core.onBg
import nl.entreco.giddyapp.core.onUi
import nl.entreco.giddyapp.viewer.HorseService
import javax.inject.Inject

class FetchHorseUsecase @Inject constructor(private val service: HorseService) {

    companion object {
        private const val BATCH_SIZE = 5
    }

    fun go(request: FetchHorseRequest, done: (FetchHorseResponse) -> Unit) {
        onBg {
            val ids = listOf(request.id, "YJ85ojXbuFfge4z1xpQS") + (1 until BATCH_SIZE).map { null }
            service.fetch(ids) { response ->
                onUi { done(FetchHorseResponse(response)) }
            }
        }
    }
}