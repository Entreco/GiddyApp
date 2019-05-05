package nl.entreco.giddyapp.libauth.user

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.GoogleAuthProvider

interface SigninMethod {
    fun credentials(): AuthCredential
}

internal class GoogleSignIn(private val googleIdToken: String) : SigninMethod {
    override fun credentials(): AuthCredential {
        return GoogleAuthProvider.getCredential(googleIdToken, null)
    }
}

internal class EmailSignIn(private val email: String, private val password: String) : SigninMethod {
    override fun credentials(): AuthCredential {
        return EmailAuthProvider.getCredential(email, password)
    }
}