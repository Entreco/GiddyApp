package nl.entreco.giddyapp.libs.horses.fetch

import androidx.annotation.WorkerThread
import nl.entreco.giddyapp.libs.horses.HorseService
import nl.entreco.giddyapp.core.onBg
import nl.entreco.giddyapp.core.onUi
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