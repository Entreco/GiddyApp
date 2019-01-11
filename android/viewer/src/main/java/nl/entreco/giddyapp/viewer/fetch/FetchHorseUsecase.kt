package nl.entreco.giddyapp.viewer.fetch

import nl.entreco.giddyapp.core.ImageCache
import nl.entreco.giddyapp.core.onBg
import nl.entreco.giddyapp.core.onUi
import nl.entreco.giddyapp.viewer.HorseService
import javax.inject.Inject

class FetchHorseUsecase @Inject constructor(
    private val cache: ImageCache,
    private val service: HorseService
) {

    companion object {
        private const val BATCH_SIZE = 10
    }

    fun go(request: FetchHorseRequest, done: (FetchHorseResponse) -> Unit) {
        onBg {
            val ids = listOf(request.id) + (1 until BATCH_SIZE).map { null }
            service.fetch(ids) { response ->
                val bitmaptized = response.mapNotNull { horse ->
                    val bmp = cache.get(horse.imageRef)
                    if (bmp != null) {
                        horse.copy(bitmap = bmp)
                    } else horse
                }
                onUi { done(FetchHorseResponse(bitmaptized)) }
            }
        }
    }
}