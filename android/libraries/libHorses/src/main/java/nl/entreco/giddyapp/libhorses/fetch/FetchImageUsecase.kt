package nl.entreco.giddyapp.libhorses.fetch

import androidx.annotation.WorkerThread
import nl.entreco.giddyapp.libcore.onBg
import nl.entreco.giddyapp.libhorses.DownloadUrl
import nl.entreco.giddyapp.libhorses.HorseService
import javax.inject.Inject

class FetchImageUsecase @Inject constructor(
    private val service: HorseService
) {

    fun go(request: FetchImageRequest, @WorkerThread done: (FetchImageResponse) -> Unit) {
        onBg {
            service.image(request.ref) { result ->
                val response = when (result) {
                    is DownloadUrl.Ok -> FetchImageResponse.Ok(request.ref, result.uri)
                    else -> FetchImageResponse.Err("Unable to retrieve uri")
                }
                done(response)
            }
        }
    }
}