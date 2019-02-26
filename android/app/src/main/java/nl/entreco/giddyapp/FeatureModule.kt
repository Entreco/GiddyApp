package nl.entreco.giddyapp

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import nl.entreco.giddyapp.core.di.App
import nl.entreco.giddyapp.core.di.AppScope

@Module
object FeatureModule {

    @App
    @Provides
    @JvmStatic
    @AppScope
    fun provideContext(app: Application): Context {
        return app
    }

    @Provides
    @JvmStatic
    @AppScope
    fun providePackageName(@App context: Context): String {
        return context.packageName
    }
}