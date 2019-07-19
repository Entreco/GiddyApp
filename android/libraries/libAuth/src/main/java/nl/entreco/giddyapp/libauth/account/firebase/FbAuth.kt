package nl.entreco.giddyapp.libauth.account.firebase

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import androidx.annotation.DrawableRes
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
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

    override fun signinIntent(@DrawableRes logo: Int): Intent = authUi
        .createSignInIntentBuilder()
        .setLogo(logo)
        .setAvailableProviders(providers)
        .enableAnonymousUsersAutoUpgrade()
        .build()

    override fun link(context: Context, resultCode: Int, data: Intent?, done: (SignupResponse) -> Unit) {
        val response = IdpResponse.fromResultIntent(data)
        when {
            resultCode == RESULT_OK -> success(response?.email ?: "Anonymous", done)
            response?.error?.errorCode == ErrorCodes.ANONYMOUS_UPGRADE_MERGE_CONFLICT -> link(context, response, done)
            else -> failed(done)
        }
    }

    private fun success(name: String, done: (SignupResponse) -> Unit) {
        done(SignupResponse.Success(name))
    }

    private fun failed(done: (SignupResponse) -> Unit) {
        done(SignupResponse.Failed("FML", -1))
    }

    private fun link(
        context: Context,
        response: IdpResponse,
        done: (SignupResponse) -> Unit
    ) {
        // TODO entreco - 2019-07-18: Refactor 1) Readability, 2) Scalability, 3) Should be Transaction/Batch
        userService.retrieve { old ->
            userService.delete { del ->
                authUi.delete(context).continueWithTask {
                    auth.signInWithCredential(response.credentialForLinking!!)
                        .addOnSuccessListener { result ->
                            val userName = result.name((old as? User.Valid)?.name ?: "FROM Linked name")
                            userService.create(userName) { usr ->
                                when (usr) {
                                    is User.Valid -> done(SignupResponse.Migrate(usr.uid, old))
                                    is User.Error -> done(SignupResponse.Failed(usr.msg, -1))
                                }
                            }
                        }
                        .addOnFailureListener { err ->
                            done(SignupResponse.Failed(err.localizedMessage, -1))
                        }
                }.addOnFailureListener { err ->
                    done(SignupResponse.Failed(err.localizedMessage, -1))
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
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val name = task.result.name("Authenticated")
                        userService.create(name) {}
                    }
                }
        }
    }

    override fun observe(key: String, done: (Account) -> Unit) {
        val listener = authListeners.getOrPut(key) {
            FirebaseAuth.AuthStateListener { _auth ->
                userService.retrieve { user ->
                    val account = _auth.currentUser
                    val name = when(user){
                        is User.Valid -> fill(user, account)
                        is User.Error -> Account.Error(user.msg)
                    }
                    done(name)
                }
            }
        }
        auth.addAuthStateListener(listener)
    }

    private fun fill(user: User.Valid, account: FirebaseUser?) : Account{
        return if(account == null || account.isAnonymous) Account.Anomymous(user.uid, user.name)
        else Account.Authenticated(user.uid, user.name, account.email ?: "No email", account.photoUrl)
    }

    override fun stopObserving(key: String) {
        authListeners.remove(key)?.let { listener ->
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

    private fun AuthResult?.name(default: String): String =
        this?.user?.displayName ?: this?.user?.providerData?.firstOrNull {
            it.displayName?.isNotBlank() ?: false
        }?.displayName ?: this?.additionalUserInfo?.username ?: if(this?.user?.isAnonymous == true) "Anonymous" else default
}