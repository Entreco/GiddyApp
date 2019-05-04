package nl.entreco.giddyapp.libauth.firebase

import com.google.firebase.auth.FirebaseAuth
import nl.entreco.giddyapp.libauth.Authenticator
import nl.entreco.giddyapp.libauth.user.*
import javax.inject.Inject

class FbAuth @Inject constructor(private val auth: FirebaseAuth) : Authenticator {
    override fun current(done: (User) -> Unit) {
        val user = auth.currentUser
        if (user != null && user.isAnonymous) done(AnomymousUser(user.uid))
        else if (user != null && !user.isAnonymous) done(AuthenticatedUser(user.uid))
        else {
            auth.signInAnonymously().addOnCompleteListener { task ->
                if (task.isSuccessful) done(AnomymousUser(auth.currentUser!!.uid))
                else done(ErrorUser(task.exception?.localizedMessage ?: "Unknown error"))
            }
        }
    }

    override fun link(provider: SigninMethod, done: (User) -> Unit) {
        auth.currentUser?.linkWithCredential(provider.credentials())?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = task.result?.user!!
                done(AuthenticatedUser(user.uid))
            } else {
                done(ErrorUser(task.exception?.localizedMessage ?: "Error linking account"))
            }
        }
    }

    override fun logout() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}