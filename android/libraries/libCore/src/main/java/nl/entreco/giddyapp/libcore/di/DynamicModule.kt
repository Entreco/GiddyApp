package nl.entreco.giddyapp.libcore.di

import android.app.Application
import dagger.Module
import dagger.Provides
import nl.entreco.giddyapp.libcore.launch.DynamicLauncher

@Module
object DynamicModule {

    @Provides
    @JvmStatic
    internal fun provideDynamicLauncher(app: Application): DynamicLauncher = DynamicLauncher(app)
}