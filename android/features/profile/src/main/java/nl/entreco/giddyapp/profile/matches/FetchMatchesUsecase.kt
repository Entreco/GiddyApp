package nl.entreco.giddyapp.profile.matches

import nl.entreco.giddyapp.libauth.user.UserLike
import nl.entreco.giddyapp.libcore.onBg
import nl.entreco.giddyapp.libmatches.MatchService
import javax.inject.Inject

class FetchMatchesUsecase @Inject constructor(
    private val matchService: MatchService
) {

    fun go(request: FetchMatchesRequest, done: (List<UserLike>) -> Unit) {
        onBg {
            when (request.uid.isEmpty()) {
                true -> done(emptyList())
                else -> continueWithMatches(request.uid, done)
            }
        }
    }

    private fun continueWithMatches(uid: String, done: (List<UserLike>) -> Unit) {
        matchService.retrieveForUser(uid) { matches ->
            done(matches.map { UserLike(it.horseId, it.name, it.ref) })
        }
    }
}