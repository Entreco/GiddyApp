package nl.entreco.giddyapp.libauth.di

import com.firebase.ui.auth.AuthUI
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import nl.entreco.giddyapp.libauth.Authenticator
import nl.entreco.giddyapp.libauth.account.firebase.FbAuth
import nl.entreco.giddyapp.libauth.user.firebase.FbUserService
import nl.entreco.giddyapp.libauth.UserService
import nl.entreco.giddyapp.libcore.di.AppScope

@Module
object AuthModule {

    @Provides
    @JvmStatic
    @AppScope
    fun provideUserService(fbApp: FirebaseApp, db: FirebaseFirestore): UserService {
        val auth = FirebaseAuth.getInstance(fbApp)
        return FbUserService(db, auth)
    }

    @Provides
    @JvmStatic
    @AppScope
    fun provideAuthenticator(fbApp: FirebaseApp, userService: UserService): Authenticator {
        val authUi = AuthUI.getInstance()
        val auth = FirebaseAuth.getInstance(fbApp)
        return FbAuth(authUi, auth, userService)
    }
}