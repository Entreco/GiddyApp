package nl.entreco.giddyapp.libauth.di

import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import nl.entreco.giddyapp.libauth.Authenticator
import nl.entreco.giddyapp.libauth.firebase.FbAuth

@Module
object AuthModule {

    @JvmStatic
    @Provides
    internal fun provideFirebaseAuth(fbApp: FirebaseApp) : FirebaseAuth {
        return FirebaseAuth.getInstance(fbApp)
    }

    @JvmStatic
    @Provides
    internal fun provideAuthenticator(fbAuth: FirebaseAuth) : Authenticator {
        return FbAuth(fbAuth)
    }
}