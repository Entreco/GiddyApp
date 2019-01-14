package nl.entreco.giddyapp.core

import android.app.Application
import android.content.Context
import android.util.Log
import dagger.Module
import dagger.Provides
import nl.entreco.giddyapp.core.images.GlideImageLoader
import nl.entreco.giddyapp.core.images.ImageLoader

@Module
class FeatureModule(private val app: Application) {

    @Provides
    @FeatureScope
    fun provideContext(): Context {
        Log.i("DAGGER", "provideContext: $app")
        return app
    }

    @Provides
    @FeatureScope
    fun providePackageName(context: Context): String {
        Log.i("DAGGER", "providePackageName")
        return context.packageName
    }
}