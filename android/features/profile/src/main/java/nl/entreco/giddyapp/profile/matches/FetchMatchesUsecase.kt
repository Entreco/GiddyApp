package nl.entreco.giddyapp.profile.matches

import nl.entreco.giddyapp.libauth.UserService
import nl.entreco.giddyapp.libauth.user.User
import nl.entreco.giddyapp.libauth.user.UserLike
import nl.entreco.giddyapp.libcore.onBg
import nl.entreco.giddyapp.libmatches.MatchService
import javax.inject.Inject

class FetchMatchesUsecase @Inject constructor(
    private val userService: UserService,
    private val matchService: MatchService
) {

    fun go(done: (List<UserLike>) -> Unit) {
        onBg {
            userService.retrieve { user ->
                when (user) {
                    is User.Valid -> continueWithMatches(user, done)
                    else -> done(emptyList())
                }
            }
        }
    }

    private fun continueWithMatches(user: User.Valid, done: (List<UserLike>) -> Unit) {
        matchService.retrieveForUser(user.uid) { matches ->
            done(matches.map { UserLike(it.horseId, it.name, it.ref) })
        }
    }
}