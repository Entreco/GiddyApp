package nl.entreco.giddyapp.viewer.fetch

import android.graphics.BitmapFactory
import android.util.Log
import androidx.annotation.WorkerThread
import nl.entreco.giddyapp.core.ImageCache
import nl.entreco.giddyapp.core.onBg
import nl.entreco.giddyapp.core.onUi
import nl.entreco.giddyapp.viewer.HorseService
import java.net.URL
import javax.inject.Inject

class FetchImageUsecase @Inject constructor(
    private val cache: ImageCache,
    private val service: HorseService
) {

    fun go(request: FetchImageRequest, @WorkerThread done: (FetchImageResponse) -> Unit) {
        onBg {

            cache.get(request.ref)?.let { bmp ->
                onUi { done(FetchImageResponse(request.ref, bmp)) }
            }

            service.image("${request.ref}.jpg") { uri ->
                onBg {

                    val url = URL(uri.toString())
                    val bitmap = BitmapFactory.decodeStream(url.openStream())

                    cache.put(request.ref, bitmap)
                    onUi { done(FetchImageResponse(request.ref, bitmap)) }
                }
            }
        }
    }
}