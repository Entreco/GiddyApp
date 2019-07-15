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

    private val providers by lazy {
        arrayListOf(
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
            resultCode == RESULT_OK -> done(SignupResponse.Success("some id"))
            response?.error?.errorCode == ErrorCodes.ANONYMOUS_UPGRADE_MERGE_CONFLICT -> link(response, done)
            else -> done(SignupResponse.Failed("FML", -1))
        }
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

    override fun silent(context: Context, done: (User) -> Unit) {
        val user = auth.currentUser
        if(user == null) {
            authUi.silentSignIn(context, providers)
                .continueWithTask { task ->
                    if (task.isSuccessful) task
                    else auth.signInAnonymously()
                }
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) get(done)
                    else done(User.Error(task.exception?.localizedMessage ?: "Oops"))
                }
        } else {
            get(done)
        }
    }

    override fun current(done: (User) -> Unit) {
        get(done)
    }

    private fun get(done: (User) -> Unit) {
        val user = auth.currentUser
        if (user != null && user.isAnonymous) done(User.Anomymous(user.uid))
        else if (user != null && !user.isAnonymous) done(User.Authenticated(user.uid, user.displayName ?: user.email ?: user.uid, user.photoUrl))
        else done(User.Error("Unknown error"))
    }

    override fun logout(context: Context, done: () -> Unit) {
        authUi.signOut(context)
            .addOnCompleteListener {
                done()
            }
    }
}