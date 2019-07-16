package nl.entreco.giddyapp.libauth.user.firebase

import nl.entreco.giddyapp.libauth.user.User

internal class UserMapper {
    fun toApiData(name: String, data: User): FbUser {
        return when (data) {
            is User.Error -> FbUser(name, emptyList(), emptyList())
            is User.Valid -> FbUser(name, data.likes, data.dislikes)
        }
    }

    fun toPoko(data: FbUser?): User {
        return if (data == null) User.Error("data is null")
        else User.Valid(data.name, data.likes, data.dislikes)
    }
}