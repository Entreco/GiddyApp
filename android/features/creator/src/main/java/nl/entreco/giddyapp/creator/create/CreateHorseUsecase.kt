package nl.entreco.giddyapp.creator.create

import android.net.Uri
import nl.entreco.giddyapp.libauth.UserService
import nl.entreco.giddyapp.libauth.user.firebase.UserHorse
import nl.entreco.giddyapp.libcore.AnkoAsyncContext
import nl.entreco.giddyapp.libcore.onBg
import nl.entreco.giddyapp.libcore.onUi
import nl.entreco.giddyapp.libhorses.*
import javax.inject.Inject

class CreateHorseUsecase @Inject constructor(
    private val userService: UserService,
    private val horseService: HorseService
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

            userService.createHorse(name) { result ->
                when (result) {
                    is UserHorse.Err -> onUi { done(CreateHorseResponse("noooooooo")) }
                    is UserHorse.Ok -> createHorse(
                        result,
                        name,
                        description,
                        gender,
                        price,
                        category,
                        level,
                        image,
                        request,
                        done
                    )
                }
            }
        }
    }

    private fun AnkoAsyncContext<CreateHorseUsecase>.createHorse(
        result: UserHorse.Ok,
        name: String,
        description: String,
        gender: HorseGender,
        price: HorsePrice,
        category: HorseCategory,
        level: HorseLevel,
        image: Uri,
        request: CreateHorseRequest,
        done: (CreateHorseResponse) -> Unit
    ) {
        horseService.create(
            result.userId,
            result.horseId,
            name,
            description,
            gender,
            price,
            category,
            level,
            image,
            request.startColor,
            request.endColor
        ) { id ->
            onUi { done(CreateHorseResponse(id)) }
        }
    }
}