package nl.entreco.giddyapp.viewer.fetch

import android.net.Uri
import nl.entreco.giddyapp.core.onBg
import nl.entreco.giddyapp.core.onUi
import nl.entreco.giddyapp.viewer.HorseService
import javax.inject.Inject

class FetchHorseUsecase @Inject constructor(private val service: HorseService) {
    fun go(request: FetchHorseRequest, done: (FetchHorseResponse) -> Unit) {
        onBg {
            service.fetch(request.id) { response ->
                response?.let { horse ->
                    onUi { done(FetchHorseResponse(horse)) }
                }
            }
        }
    }

    fun image(ref: String, done: (Uri) -> Unit) {
        onBg {
            service.image(ref) { uri ->
                onUi { done(uri) }
            }
        }
    }
}