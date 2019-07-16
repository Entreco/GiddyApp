package nl.entreco.giddyapp.viewer.ratings

import nl.entreco.giddyapp.libauth.UserService
import nl.entreco.giddyapp.libauth.user.UserLike
import nl.entreco.giddyapp.libcore.onBg
import nl.entreco.giddyapp.libcore.onUi
import nl.entreco.giddyapp.libhorses.HorseService
import javax.inject.Inject

class RateHorseUsecase @Inject constructor(
    private val horseService: HorseService,
    private val userService: UserService
) {

    private val requests = mutableListOf<RateHorseRequest>()

    fun store(request: RateHorseRequest) {
        onBg {
            requests.add(request)
        }
    }

    fun go(done: (RateHorseResponse) -> Unit) {
        onBg {
            val likes = requests.filter { it.like }.map { UserLike(it.horseId, it.horseName, it.horseRef) }
            val dislikes = requests.filter { !it.like }.map { UserLike(it.horseId, it.horseName, it.horseRef) }
            userService.rate(likes, dislikes) {
                horseService.rate(likes.map { it.horseId }, dislikes.map { it.horseId }) { result ->
                    onUi { done(RateHorseResponse(result)) }
                }
            }
        }
    }
}