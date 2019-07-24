package nl.entreco.giddyapp

import android.app.Application
import android.content.Context
import com.google.firebase.FirebaseApp
import dagger.Module
import dagger.Provides
import nl.entreco.giddyapp.libcore.di.AppContext
import nl.entreco.giddyapp.libcore.di.AppScope

@Module
object FeatureModule {

    @AppContext
    @Provides
    @JvmStatic
    @AppScope
    fun provideContext(app: Application): Context {
        return app
    }

    @Provides
    @JvmStatic
    @AppScope
    fun providePackageName(@AppContext context: Context): String {
        return context.packageName
    }

    @Provides
    @JvmStatic
    @AppScope
    fun provideFirebaseApp(): FirebaseApp {
        return FirebaseApp.getInstance()
    }

}