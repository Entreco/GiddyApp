package nl.entreco.giddyapp.libauth.account.firebase

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import nl.entreco.giddyapp.libauth.Authenticator
import nl.entreco.giddyapp.libauth.UserService
import nl.entreco.giddyapp.libauth.account.Account
import nl.entreco.giddyapp.libauth.account.SignupResponse
import nl.entreco.giddyapp.libauth.user.User
import javax.inject.Inject


internal class FbAuth @Inject constructor(
    private val authUi: AuthUI,
    private val auth: FirebaseAuth,
    private val userService: UserService
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

    override fun signinIntent(): Intent = authUi
        .createSignInIntentBuilder()
        .setAvailableProviders(providers)
        .enableAnonymousUsersAutoUpgrade()
        .build()

    override fun link(context: Context, resultCode: Int, data: Intent?, done: (SignupResponse) -> Unit) {
        val response = IdpResponse.fromResultIntent(data)
        when {
            resultCode == RESULT_OK -> success(done)
            response?.error?.errorCode == ErrorCodes.ANONYMOUS_UPGRADE_MERGE_CONFLICT -> link(context, response, done)
            else -> failed(done)
        }
    }

    private fun success(done: (SignupResponse) -> Unit) {
        done(SignupResponse.Success("some id"))
    }

    private fun failed(done: (SignupResponse) -> Unit) {
        done(SignupResponse.Failed("FML", -1))
    }

    private fun link(
        context: Context,
        response: IdpResponse,
        done: (SignupResponse) -> Unit
    ) {
        // Store relevant anonymous user data
        val oldUuid = auth.currentUser?.uid ?: ""
        userService.retrieveAndDelete(oldUuid){ oldUserData ->
            // Delete Old Account
            authUi.delete(context).continueWithTask {
                // Link
                auth.signInWithCredential(response.credentialForLinking!!)
                    .addOnSuccessListener { result ->
                        // Copy over data
                        result.user.reload().addOnSuccessListener { _ ->
                            userService.create(result.user.uid, oldUserData){ userData ->
                                done(SignupResponse.Success(result.user.uid, oldUuid))
                            }
                        }
                    }
            }
        }
    }

    override fun signinOrAnonymous(context: Context) {
        val user = auth.currentUser
        if (user == null) {
            authUi.silentSignIn(context, providers)
                .continueWithTask { task ->
                    if (task.isSuccessful) task
                    else auth.signInAnonymously()
                }
                .addOnCompleteListener {
                    userService.create(auth.currentUser?.uid ?: "DOH", User(auth.currentUser?.displayName ?: "DISDOH", emptyList(), emptyList())){}
                }
        }
    }

    override fun observe(key: String, done: (Account) -> Unit) {
        val listener = authListeners.getOrPut(key) {
            FirebaseAuth.AuthStateListener { _auth ->
                val user = _auth.currentUser
                if (user != null && user.isAnonymous) done(Account.Anomymous(user.uid))
                else if (user != null && !user.isAnonymous) done(
                    Account.Authenticated(
                        user.uid,
                        user.displayName ?: user.email ?: user.uid,
                        user.email ?: user.phoneNumber ?: user.uid,
                        user.photoUrl
                    )
                )
                else done(Account.Error("Unknown error"))
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
            auth.removeAuthStateListener(listener)
        }
    }

    override fun logout(context: Context, done: () -> Unit) {
        authUi.signOut(context)
            .addOnCompleteListener {
                done()
            }
    }

    override fun delete(uuid: String) {

    }
}