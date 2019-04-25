package nl.entreco.giddyapp.libcore.di

import android.app.Application
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import dagger.Module
import dagger.Provides
import nl.entreco.giddyapp.libcore.launch.DynamicLauncher

@Module
object DynamicModule {

    @Provides
    @AppScope
    @JvmStatic
    internal fun provideSplitInstallManager(app: Application) = SplitInstallManagerFactory.create(app)

    @Provides
    @AppScope
    @JvmStatic
    internal fun provideDynamicLauncher(manager: SplitInstallManager) = DynamicLauncher(manager)
}