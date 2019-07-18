package nl.entreco.giddyapp.libauth

import nl.entreco.giddyapp.libauth.user.DeleteResponse
import nl.entreco.giddyapp.libauth.user.User
import nl.entreco.giddyapp.libauth.user.UserLike

interface UserService {
    fun create(name: String, likes: List<UserLike>, done:(User)->Unit)
    fun retrieve(done:(User)->Unit)
    fun rate(likes: List<UserLike>, dislikes: List<UserLike>, done:()->Unit)
    fun delete(done:(DeleteResponse)->Unit)
}