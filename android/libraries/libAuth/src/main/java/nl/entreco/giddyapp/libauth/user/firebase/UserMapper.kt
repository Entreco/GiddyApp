package nl.entreco.giddyapp.libauth.user.firebase

import nl.entreco.giddyapp.libauth.user.User
import nl.entreco.giddyapp.libauth.user.UserLike

internal class UserMapper {
    fun toApiData(name: String, data: User): FbUser {
        return when (data) {
            is User.Error -> FbUser(name)
            is User.Valid -> FbUser(name)
        }
    }

    fun toUser(data: FbUser?, likes: List<FbUserLike>, dislikes: List<FbUserLike>): User {
        return if (data == null) User.Error("data is null")
        else User.Valid(data.name, toUserLikes(likes), toUserLikes(dislikes))
    }

    private fun toUserLikes(likes: List<FbUserLike>?): List<UserLike> {
        return likes?.map { UserLike(it.id, it.name, it.ref) } ?: emptyList()
    }
}