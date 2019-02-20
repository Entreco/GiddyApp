package nl.entreco.giddyapp.libs.horses.create

import android.graphics.BitmapFactory
import android.util.Log
import androidx.core.graphics.BitmapCompat
import nl.entreco.giddyapp.libs.horses.HorseService
import nl.entreco.giddyapp.core.onBg
import nl.entreco.giddyapp.core.onUi
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