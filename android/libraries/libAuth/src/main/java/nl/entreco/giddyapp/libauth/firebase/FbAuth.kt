package nl.entreco.giddyapp.libauth.firebase

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import nl.entreco.giddyapp.libauth.Authenticator
import nl.entreco.giddyapp.libauth.user.SignupResponse
import nl.entreco.giddyapp.libauth.user.User
import javax.inject.Inject


internal class FbAuth @Inject constructor(
    private val authUi: AuthUI,
    private val auth: FirebaseAuth
) : Authenticator {

    private val authListeners = mutableMapOf<String, FirebaseAuth.AuthStateListener>()

    private val providers by lazy {
        listOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
//            AuthUI.IdpConfig.PhoneBuilder().build(),
//            AuthUI.IdpConfig.FacebookBuilder().build(),
//            AuthUI.IdpConfig.TwitterBuilder().build()
        )
    }

    override fun upgrade(): Intent = authUi
        .createSignInIntentBuilder()
        .setAvailableProviders(providers)
        .enableAnonymousUsersAutoUpgrade()
        .build()

    override fun merge(resultCode: Int, data: Intent?, done: (SignupResponse) -> Unit) {
        val response = IdpResponse.fromResultIntent(data)
        when {
            resultCode == RESULT_OK -> success(done)
            response?.error?.errorCode == ErrorCodes.ANONYMOUS_UPGRADE_MERGE_CONFLICT -> link(response, done)
            else -> failed(done)
        }
    }

    private fun success(done: (SignupResponse) -> Unit) {
        auth.currentUser?.reload()?.addOnCompleteListener {
            done(SignupResponse.Success("some id"))
        }
    }

    private fun failed(done: (SignupResponse) -> Unit) {
        done(SignupResponse.Failed("FML", -1))
    }

    private fun link(
        response: IdpResponse,
        done: (SignupResponse) -> Unit
    ) {
        // Store relevant anonymous user data
        auth.signInWithCredential(response.credentialForLinking!!)
            .addOnSuccessListener { result ->
                // Copy over data
                done(SignupResponse.Success("another id"))
            }
    }

    override fun silent(context: Context) {
        val user = auth.currentUser
        if (user == null) {
            authUi.silentSignIn(context, providers)
                .continueWithTask { task ->
                    if (task.isSuccessful) task
                    else auth.signInAnonymously()
                }
                .addOnCompleteListener { }
        }
    }

    override fun observe(key: String, done: (User) -> Unit) {
        val listener = authListeners.getOrPut(key) {
            FirebaseAuth.AuthStateListener { _auth ->
                val user = _auth.currentUser
                if (user != null && user.isAnonymous) done(User.Anomymous(user.uid))
                else if (user != null && !user.isAnonymous) done(
                    User.Authenticated(
                        user.uid,
                        user.displayName ?: user.email ?: user.uid,
                        user.email ?: user.phoneNumber ?: user.uid,
                        user.photoUrl
                    )
                )
                else done(User.Error("Unknown error"))
            }
        }
        auth.addAuthStateListener(listener)
    }

    override fun stopObserving(key: String) {
        authListeners.remove(key)?.let { listener ->
            auth.removeAuthStateListener(listener)
        }
    }

    override fun clearAllObservers() {
        authListeners.values.forEach { listener ->
            auth.removeAuthStateListener(listener) }
    }

    override fun logout(context: Context, done: () -> Unit) {
        authUi.signOut(context)
            .addOnCompleteListener {
                done()
            }
    }
}