package nl.entreco.giddyapp.libauth.firebase

import android.content.Intent
import com.firebase.ui.auth.AuthUI
import nl.entreco.giddyapp.libauth.Signup

class FbSignup(private val authUi: AuthUI) : Signup {
    override fun signup() : Intent = authUi.createSignInIntentBuilder().build()
}