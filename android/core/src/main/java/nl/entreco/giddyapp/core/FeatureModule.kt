package nl.entreco.giddyapp.core

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class FeatureModule(private val app: Application) {

    @Provides
    @FeatureScope
    fun provideContext(): Context {
        return app
    }

    @Provides
    @FeatureScope
    fun providePackageName(context: Context): String {
        return context.packageName
    }
}