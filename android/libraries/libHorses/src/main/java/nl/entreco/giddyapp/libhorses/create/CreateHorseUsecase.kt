package nl.entreco.giddyapp.libhorses.create

import nl.entreco.giddyapp.libhorses.HorseService
import nl.entreco.giddyapp.libcore.onBg
import nl.entreco.giddyapp.libcore.onUi
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
            val price = request.price
            val category = request.category
            val level = request.level

            service.create(name, description, gender, price, category, level, image, request.startColor, request.endColor){ id ->
                onUi { done(CreateHorseResponse(id)) }
            }
        }
    }
}