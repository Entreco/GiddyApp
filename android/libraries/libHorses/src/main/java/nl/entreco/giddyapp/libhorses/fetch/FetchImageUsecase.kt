package nl.entreco.giddyapp.libhorses.fetch

import android.util.Log
import androidx.annotation.WorkerThread
import nl.entreco.giddyapp.libcore.onBg
import nl.entreco.giddyapp.libcore.onUi
import nl.entreco.giddyapp.libhorses.DownloadUrl
import nl.entreco.giddyapp.libhorses.HorseService
import javax.inject.Inject

class FetchImageUsecase @Inject constructor(
    private val service: HorseService
) {
    fun go(request: FetchImageRequest, @WorkerThread done: (FetchImageResponse) -> Unit) {
        onBg {
            service.image(request.ref) { result ->
                when (result) {
                    is DownloadUrl.Ok -> onUi { done(FetchImageResponse(request.ref, result.uri)) }
                    else -> Log.w("DOH", "Unable to retrieve uri")
                }
            }
        }
    }
}