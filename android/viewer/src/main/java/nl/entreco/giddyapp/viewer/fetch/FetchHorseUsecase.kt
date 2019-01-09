package nl.entreco.giddyapp.viewer.fetch

import android.net.Uri
import nl.entreco.giddyapp.core.onBg
import nl.entreco.giddyapp.core.onUi
import nl.entreco.giddyapp.viewer.Horse
import nl.entreco.giddyapp.viewer.HorseService
import javax.inject.Inject
import kotlin.random.Random

class FetchHorseUsecase @Inject constructor(private val service: HorseService) {
    fun go(request: FetchHorseRequest, done: (FetchHorseResponse) -> Unit) {
        onBg {
//            service.fetch(request.id) { response ->
//                response?.let { horse ->
//                    onUi { done(FetchHorseResponse(horse)) }
//                }
//            }

            val random = Random.nextInt(100)
            val horse1 = Horse("Charlie$random", "#ff6600", "#0066ff", "none")
            val horse2 = Horse("Santa$random", "#aaba12", "#abbec3", "none")
            onUi { done(FetchHorseResponse(horse1, horse2)) }
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