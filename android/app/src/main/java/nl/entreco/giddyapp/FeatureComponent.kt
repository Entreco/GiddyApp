package nl.entreco.giddyapp

import android.app.Application
import android.content.Context
import android.util.DisplayMetrics
import com.google.firebase.FirebaseApp
import dagger.BindsInstance
import dagger.Component
import nl.entreco.giddyapp.libauth.Authenticator
import nl.entreco.giddyapp.libauth.di.AuthModule
import nl.entreco.giddyapp.libcore.di.AppContext
import nl.entreco.giddyapp.libcore.di.AppScope
import nl.entreco.giddyapp.libcore.di.DynamicModule
import nl.entreco.giddyapp.libcore.launch.DynamicLauncher
import nl.entreco.giddyapp.libhorses.HorseService
import nl.entreco.giddyapp.libhorses.di.HorseModule
import nl.entreco.giddyapp.libimg.di.ImageModule
import nl.entreco.giddyapp.libimg.loader.ImageLoader

@AppScope
@Component(modules = [FeatureModule::class, DynamicModule::class, HorseModule::class, ImageModule::class, AuthModule::class])
interface FeatureComponent {
    @AppContext
    fun appContext(): Context

    fun auth(): Authenticator
    fun horseService(): HorseService
    fun imageLoader(): ImageLoader
    fun metrics(): DisplayMetrics
    fun fbApp(): FirebaseApp
    fun dynamicLauncher(): DynamicLauncher

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun app(app: Application): Builder

        @BindsInstance
        fun metrics(metrics: DisplayMetrics): Builder

        fun build(): FeatureComponent
    }
}