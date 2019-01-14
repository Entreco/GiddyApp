package nl.entreco.giddyapp.viewer.fetch

import androidx.annotation.WorkerThread
import nl.entreco.giddyapp.core.images.ImageCache
import nl.entreco.giddyapp.core.images.ImageLoader
import nl.entreco.giddyapp.core.onBg
import nl.entreco.giddyapp.core.onUi
import nl.entreco.giddyapp.viewer.HorseService
import javax.inject.Inject

class FetchImageUsecase @Inject constructor(
    private val cache: ImageCache,
    private val imageLoader: ImageLoader,
    private val service: HorseService
) {

    fun go(request: FetchImageRequest, @WorkerThread done: (FetchImageResponse) -> Unit) {
        onBg {

            cache.get(request.ref)?.let { bmp ->
                onUi { done(FetchImageResponse(request.ref, bmp)) }
            }

            service.image("${request.ref}.jpg") { uri ->
                onBg {
                    val bitmap = imageLoader.get(uri)
                    cache.put(request.ref, bitmap)
                    onUi { done(FetchImageResponse(request.ref, bitmap)) }
                }
            }
        }
    }
}