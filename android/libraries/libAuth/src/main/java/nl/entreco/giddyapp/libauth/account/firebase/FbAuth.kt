package nl.entreco.giddyapp.libauth.account.firebase

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.firebase.ui.auth.util.ExtraConstants
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import nl.entreco.giddyapp.libauth.Authenticator
import nl.entreco.giddyapp.libauth.BuildConfig
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

    private val email by lazy {
        ActionCodeSettings.newBuilder()
            .setAndroidPackageName(packageName(), true, null)
            .setHandleCodeInApp(true)
            .setDynamicLinkDomain("giddy.page.link/email")
            .setUrl("https://giddy.entreco.nl/email") // This URL needs to be whitelisted
            .build()
    }

    private fun packageName() = when (BuildConfig.FLAVOR) {
        "beta" -> "nl.entreco.giddyapp.dev"
        else -> "nl.entreco.giddyapp"
    }

    private val providers by lazy {
        listOf(
            AuthUI.IdpConfig.EmailBuilder().setActionCodeSettings(email).enableEmailLinkSignIn().build(),
            AuthUI.IdpConfig.PhoneBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )
    }

    override fun signinIntent(settings: FbAuthUiSettings, link: String?): Intent {
        val layout = settings.build()
        val builder = authUi
            .createSignInIntentBuilder()
            .setTheme(settings.style)
            .setAuthMethodPickerLayout(layout)
            .setAvailableProviders(providers)
            .setIsSmartLockEnabled(!BuildConfig.DEBUG)
            .enableAnonymousUsersAutoUpgrade()
            .setTosAndPrivacyPolicyUrls(
                "https://giddy.entreco.nl/privacy_policy.html",
                "https://giddy.entreco.nl/privacy_policy.html"
            )

        if (link?.isNotBlank() == true) builder.setEmailLink(link)

        return builder.build()

    }

    override fun canHandle(intent: Intent, done: (String?) -> Unit) {
        if (AuthUI.canHandleIntent(intent)) {
            // TODO entreco - 2019-07-26: Official Documenation is using link1, but it does not work
            val link1 = intent.extras?.getString(ExtraConstants.EMAIL_LINK_SIGN_IN)
            val link2 = intent.dataString
            done(link2)
        } else {
            done(null)
        }
    }

    override fun link(
        context: Context,
        resultCode: Int,
        data: Intent?,
        done: (SignupResponse) -> Unit
    ) {
        val response = IdpResponse.fromResultIntent(data)
        when {
            response == null -> cancelled(done)
            response.error?.errorCode == ErrorCodes.ANONYMOUS_UPGRADE_MERGE_CONFLICT -> link(
                context,
                response,
                done
            )
            resultCode == RESULT_OK -> success(response.email ?: "Linked", done)
            else -> failed(response.error?.localizedMessage ?: "FML", done)
        }
    }

    private fun success(name: String, done: (SignupResponse) -> Unit) {
        userService.create(auth.currentUser?.displayName ?: name) {
            done(SignupResponse.Success(name))
        }
    }

    private fun cancelled(done: (SignupResponse) -> Unit) {
        done(SignupResponse.Cancelled)
    }

    private fun failed(error: String, done: (SignupResponse) -> Unit) {
        done(SignupResponse.Failed(error, -1))
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
                            val userName = result.name((old as? User.Valid)?.name ?: "Linked name")
                            userService.create(userName) { usr ->
                                when (usr) {
                                    is User.Valid -> done(SignupResponse.Migrate(usr.uid, old))
                                    is User.Anonymous -> done(
                                        SignupResponse.Failed(
                                            "User is still anonymous",
                                            -1
                                        )
                                    )
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

    override fun signinOrAnonymous(context: Context, done: () -> Unit) {
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
                        userService.create(name) {
                            done()
                        }
                    } else {
                        done()
                    }
                }
        } else {
            done()
        }
    }

    override fun observe(key: String, done: (Account) -> Unit) {
        auth.addAuthStateListener { _auth ->
            userService.retrieve { user ->
                val account = _auth.currentUser
                val name = when (user) {
                    is User.Valid -> fill(user, account)
                    is User.Anonymous -> Account.Anomymous(user.uid, "Damn you Anonymous")
                    is User.Error -> Account.Error(user.msg)
                }
                done(name)
            }
        }
    }

    private fun fill(user: User.Valid, account: FirebaseUser?): Account {
        return if (account == null || account.isAnonymous) Account.Anomymous(user.uid, user.name)
        else Account.Authenticated(
            user.uid,
            user.name,
            account.email ?: account.phoneNumber ?: "No email",
            account.photoUrl
        )
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
        }?.displayName ?: this?.additionalUserInfo?.username
        ?: if (this?.user?.isAnonymous == true) "Anonymous" else default
}