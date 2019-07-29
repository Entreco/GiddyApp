package nl.entreco.giddyapp.viewer.ratings

import nl.entreco.giddyapp.libauth.UserService
import nl.entreco.giddyapp.libauth.user.User
import nl.entreco.giddyapp.libcore.AnkoAsyncContext
import nl.entreco.giddyapp.libcore.onBg
import nl.entreco.giddyapp.libcore.onUi
import nl.entreco.giddyapp.libhorses.HorseRating
import nl.entreco.giddyapp.libhorses.HorseService
import nl.entreco.giddyapp.libmatches.Match
import nl.entreco.giddyapp.libmatches.MatchResponse
import nl.entreco.giddyapp.libmatches.MatchService
import javax.inject.Inject

class RateHorseUsecase @Inject constructor(
    private val horseService: HorseService,
    private val userService: UserService,
    private val matchService: MatchService
) {

    private val requests = mutableListOf<RateHorseRequest>()

    fun store(request: RateHorseRequest) {
        onBg {
            requests.add(request)
        }
    }

    fun go(done: (RateHorseResponse) -> Unit) {
        onBg {

            val likes = requests
                .filter { it.like && it.horseRef != "none" }
                .map { Match(it.horseId, it.horseName, it.horseRef) }

            val dislikes = requests
                .filter { !it.like && it.horseRef != "none" }
                .map { Match(it.horseId, it.horseName, it.horseRef) }

            if (likes.isEmpty()) onUi { done(RateHorseResponse(true)) }
            else {
                userService.retrieve { user ->
                    when (user) {
                        is User.Valid -> continueWithMatchRating(user.uid, likes, dislikes, done)
                        is User.Anonymous -> continueWithMatchRating(user.uid, likes, dislikes, done)
                        is User.Error -> onUi { done(RateHorseResponse(false)) }
                    }
                }
            }
        }
    }

    private fun AnkoAsyncContext<RateHorseUsecase>.continueWithMatchRating(
        userId: String,
        likes: List<Match>,
        dislikes: List<Match>,
        done: (RateHorseResponse) -> Unit
    ) {
        matchService.match(userId, likes) { result ->
            when (result) {
                is MatchResponse.Ok -> continueWithHorseRating(likes, dislikes, done)
                is MatchResponse.Failed -> onUi { done(RateHorseResponse(false)) }
            }
        }
    }

    private fun AnkoAsyncContext<RateHorseUsecase>.continueWithHorseRating(
        likes: List<Match>,
        dislikes: List<Match>,
        done: (RateHorseResponse) -> Unit
    ) {
        horseService.rate(likes.map { it.horseId }, dislikes.map { it.horseId }) { result ->

            val response = when (result) {
                is HorseRating.Ok -> {
                    requests.clear()
                    true
                }
                is HorseRating.Failed -> false
            }

            onUi { done(RateHorseResponse(response)) }
        }
    }
}