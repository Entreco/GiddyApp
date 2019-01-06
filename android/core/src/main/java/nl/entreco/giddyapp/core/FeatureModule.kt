package nl.entreco.giddyapp.core

import android.app.Application
import android.content.Context
import android.util.Log
import dagger.Module
import dagger.Provides

@Module
class FeatureModule(private val app: Application) {

    @Provides
    fun provideContext(): Context {
        Log.i("DAGGER", "provideContext: $app")
        return app
    }

    @Provides
    fun providePackageName(context: Context): String {
        Log.i("DAGGER", "providePackageName")
        return context.packageName
    }
}