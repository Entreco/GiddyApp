package nl.entreco.giddyapp.libauth

import nl.entreco.giddyapp.libauth.user.DeleteResponse
import nl.entreco.giddyapp.libauth.user.User

interface UserService {
    fun create(userData:User, done:(User)->Unit)
    fun retrieve(done:(User)->Unit)
    fun rate(likes: List<String>, dislikes: List<String>, done:()->Unit)
    fun delete(done:(DeleteResponse)->Unit)
}