package nl.entreco.giddyapp.profile.matches

import nl.entreco.giddyapp.libauth.UserService
import nl.entreco.giddyapp.libauth.user.User
import nl.entreco.giddyapp.libauth.user.UserLike
import nl.entreco.giddyapp.libcore.onBg
import nl.entreco.giddyapp.libcore.onUi
import nl.entreco.giddyapp.libhorses.Horse
import nl.entreco.giddyapp.libhorses.HorseService
import nl.entreco.giddyapp.libhorses.fetch.FilterOptions
import javax.inject.Inject

class FetchMatchesUsecase @Inject constructor(
    private val userService: UserService,
    private val horseService: HorseService
) {

    fun go(done: (List<UserLike>) -> Unit) {
        onBg {
            userService.retrieve { user ->
                val likes = when (user) {
                    is User.Valid -> user.likes
                    else -> emptyList()
                }
                done(likes)
            }
        }
    }
}