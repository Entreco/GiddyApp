package nl.entreco.giddyapp.libauth.user.firebase

import nl.entreco.giddyapp.libauth.user.User

internal class UserMapper {

    fun toUser(uid: String, data: FbUser?): User {
        return if (data == null) User.Error("data is null")
        else User.Valid(uid, data.name)
    }
}