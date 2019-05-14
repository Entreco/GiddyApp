package nl.entreco.giddyapp.libauth.firebase

import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import nl.entreco.giddyapp.libauth.Authenticator
import nl.entreco.giddyapp.libauth.user.SigninMethod
import nl.entreco.giddyapp.libauth.user.User
import javax.inject.Inject

internal class FbAuth @Inject constructor(private val auth: FirebaseAuth) : Authenticator {
    override fun current(done: (User) -> Unit) {
        val user = auth.currentUser
        if (user != null && user.isAnonymous) done(User.Anomymous(user.uid))
        else if (user != null && !user.isAnonymous) done(User.Authenticated(user.uid))
        else {
            auth.signInAnonymously().addOnCompleteListener { task ->
                if (task.isSuccessful) done(User.Anomymous(auth.currentUser!!.uid))
                else done(User.Error(task.exception?.localizedMessage ?: "Unknown error"))
            }
        }
    }

    override fun link(provider: SigninMethod, done: (User) -> Unit) {
        val credentials = when (provider) {
            is SigninMethod.Google -> GoogleAuthProvider.getCredential(provider.googleIdToken, null)
            is SigninMethod.EmailSignIn -> EmailAuthProvider.getCredential(provider.email, provider.password)
        }

        auth.currentUser?.linkWithCredential(credentials)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = task.result?.user!!
                done(User.Authenticated(user.uid))
            } else {
                done(User.Error(task.exception?.localizedMessage ?: "Error linking account"))
            }
        }
    }

    override fun logout() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}