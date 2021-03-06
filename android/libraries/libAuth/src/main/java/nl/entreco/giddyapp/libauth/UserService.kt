package nl.entreco.giddyapp.libauth

import nl.entreco.giddyapp.libauth.user.DeleteResponse
import nl.entreco.giddyapp.libauth.user.User
import nl.entreco.giddyapp.libauth.user.firebase.UserHorse

interface UserService {
    fun create(name: String, done: (User) -> Unit)
    fun createHorse(name: String, done: (UserHorse) -> Unit)

    fun retrieve(done: (User) -> Unit)
    fun delete(done: (DeleteResponse) -> Unit)
}