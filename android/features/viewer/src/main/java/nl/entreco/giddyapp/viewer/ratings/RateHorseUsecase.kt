package nl.entreco.giddyapp.viewer.ratings

import nl.entreco.giddyapp.libauth.UserService
import nl.entreco.giddyapp.libauth.user.UserLike
import nl.entreco.giddyapp.libauth.user.UserRating
import nl.entreco.giddyapp.libcore.AnkoAsyncContext
import nl.entreco.giddyapp.libcore.onBg
import nl.entreco.giddyapp.libcore.onUi
import nl.entreco.giddyapp.libhorses.HorseRating
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

            userService.rate(likes, dislikes) { result ->
                when (result) {
                    is UserRating.Ok -> continueWithHorseRating(likes, dislikes, done)
                    is UserRating.Err -> onUi { done(RateHorseResponse(false)) }
                }
            }
        }
    }

    private fun AnkoAsyncContext<RateHorseUsecase>.continueWithHorseRating(
        likes: List<UserLike>,
        dislikes: List<UserLike>,
        done: (RateHorseResponse) -> Unit
    ) {
        horseService.rate(likes.map { it.horseId }, dislikes.map { it.horseId }) { result ->

            val response = when (result) {
                is HorseRating.Ok -> true
                is HorseRating.Failed -> false
            }

            onUi { done(RateHorseResponse(response)) }
        }
    }
}