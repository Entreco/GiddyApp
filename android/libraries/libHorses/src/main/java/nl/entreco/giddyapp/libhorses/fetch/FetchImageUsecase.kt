package nl.entreco.giddyapp.libhorses.fetch

import androidx.annotation.WorkerThread
import nl.entreco.giddyapp.libhorses.HorseService
import nl.entreco.giddyapp.libcore.onBg
import nl.entreco.giddyapp.libcore.onUi
import javax.inject.Inject

class FetchImageUsecase @Inject constructor(
    private val service: HorseService
) {

    fun go(request: FetchImageRequest, @WorkerThread done: (FetchImageResponse) -> Unit) {
        onBg {

            service.image("${request.ref}.${request.ext}") { uri ->
                onBg {
                    onUi { done(FetchImageResponse(request.ref, uri)) }
                }
            }
        }
    }
}