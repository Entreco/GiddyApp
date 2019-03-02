package nl.entreco.giddyapp

import android.app.Application
import android.content.Context
import android.util.DisplayMetrics
import com.google.firebase.FirebaseApp
import dagger.BindsInstance
import dagger.Component
import nl.entreco.giddyapp.libcore.di.App
import nl.entreco.giddyapp.libcore.di.AppScope
import nl.entreco.giddyapp.libhorses.HorseService
import nl.entreco.giddyapp.libhorses.di.HorseModule
import nl.entreco.giddyapp.libimg.di.ImageModule
import nl.entreco.giddyapp.libimg.loader.ImageLoader

@AppScope
@Component(modules = [FeatureModule::class, HorseModule::class, ImageModule::class])
interface FeatureComponent {
    @App
    fun appContext(): Context

    fun horseService(): HorseService
    fun imageLoader(): ImageLoader
    fun metrics(): DisplayMetrics
    fun fbApp(): FirebaseApp

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun app(app: Application): Builder

        @BindsInstance
        fun metrics(metrics: DisplayMetrics): Builder

        fun build(): FeatureComponent
    }
}