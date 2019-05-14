package nl.entreco.giddyapp.libauth.di

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
        val fbAuth = FirebaseAuth.getInstance(fbApp)
        return FbAuth(fbAuth)
    }
}