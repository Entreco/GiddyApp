package nl.entreco.giddyapp.libauth.user.firebase

import com.google.firebase.auth.FirebaseUser
import nl.entreco.giddyapp.libauth.user.User

internal class UserMapper {

    fun toUser(uid: String, authUser: FirebaseUser, userData: FbUser?): User {
        return when {
            userData == null -> User.Error("userData is null")
            authUser.isAnonymous -> User.Anonymous(uid)
            else -> User.Valid(uid, authUser.displayName ?: userData.name)
        }
    }
}