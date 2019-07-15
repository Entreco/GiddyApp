package nl.entreco.giddyapp.libauth

import nl.entreco.giddyapp.libauth.user.User

interface UserService {
    fun create(uuid: String, userData:User, done:(User)->Unit)
    fun retrieveAndDelete(uuid: String, done:(User)->Unit)
}