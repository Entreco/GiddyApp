package nl.entreco.giddyapp

import android.app.Application
import android.content.Context
import android.util.DisplayMetrics
import dagger.BindsInstance
import dagger.Component
import nl.entreco.giddyapp.core.di.App
import nl.entreco.giddyapp.core.di.AppScope
import nl.entreco.giddyapp.lib.img.di.ImageModule
import nl.entreco.giddyapp.lib.img.loader.ImageLoader
import nl.entreco.giddyapp.libs.horses.HorseService
import nl.entreco.giddyapp.libs.horses.di.HorseModule

@AppScope
@Component(modules = [FeatureModule::class, HorseModule::class, ImageModule::class])
interface FeatureComponent {
    @App
    fun appContext(): Context

    fun horseService(): HorseService
    fun imageLoader(): ImageLoader
    fun metrics(): DisplayMetrics

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun app(app: Application): Builder

        @BindsInstance
        fun metrics(metrics: DisplayMetrics): Builder

        fun build(): FeatureComponent
    }
}