package nl.entreco.giddyapp.libauth.di

import com.firebase.ui.auth.AuthUI
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import nl.entreco.giddyapp.libauth.Authenticator
import nl.entreco.giddyapp.libauth.firebase.FbAuth

@Module
object AuthModule {

    @Provides
    @JvmStatic
    fun provideAuthenticator(fbApp: FirebaseApp): Authenticator {
        val authUi = AuthUI.getInstance()
        val auth = FirebaseAuth.getInstance(fbApp)
        return FbAuth(authUi, auth)
    }
}