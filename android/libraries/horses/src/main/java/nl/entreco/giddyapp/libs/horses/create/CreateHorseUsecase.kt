package nl.entreco.giddyapp.libs.horses.create

import nl.entreco.giddyapp.libs.horses.HorseService
import nl.entreco.giddyapp.libs.horses.onBg
import nl.entreco.giddyapp.libs.horses.onUi
import javax.inject.Inject

class CreateHorseUsecase @Inject constructor(
    private val service: HorseService
) {

    fun go(request: CreateHorseRequest, done: (CreateHorseResponse) -> Unit) {
        onBg {
            val name = request.name
            val description = request.description
            val gender = request.gender
            val image = request.image
            service.create(name, description, gender, image, request.startColor, request.endColor){ id ->
                onUi { done(CreateHorseResponse(id)) }
            }
        }
    }
}