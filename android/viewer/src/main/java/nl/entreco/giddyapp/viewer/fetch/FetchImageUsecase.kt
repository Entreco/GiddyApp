package nl.entreco.giddyapp.viewer.fetch

import android.graphics.BitmapFactory
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import androidx.annotation.WorkerThread
import nl.entreco.giddyapp.core.onBg
import nl.entreco.giddyapp.core.onUi
import nl.entreco.giddyapp.viewer.HorseService
import java.net.URL
import javax.inject.Inject

class FetchImageUsecase @Inject constructor(private val service: HorseService) {

    fun go(request: FetchImageRequest, @WorkerThread done: (FetchImageResponse) -> Unit) {
        val thread = HandlerThread("doh")
        thread.start()
        Handler(thread.looper).post{
            service.image(request.ref) { uri ->
                val url = URL(uri.toString())

                onBg {
                    val bitmap = BitmapFactory.decodeStream(url.openStream())
                    done(FetchImageResponse(bitmap))
                }
            }
        }
    }
}