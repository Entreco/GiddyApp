package nl.entreco.giddyapp.creator.di

import android.app.Activity
import android.content.Context
import dagger.BindsInstance
import dagger.Component
import nl.entreco.giddyapp.core.di.App
import nl.entreco.giddyapp.creator.CreatorViewModel
import nl.entreco.giddyapp.lib.img.loader.ImageLoader
import nl.entreco.giddyapp.libpicker.ImagePicker
import nl.entreco.giddyapp.libpicker.PickerModule
import nl.entreco.giddyapp.libs.horses.HorseService

@Component(
    modules = [CreatorModule::class, PickerModule::class]
)
interface CreatorComponent {
    fun viewModel(): CreatorViewModel
    fun picker(): ImagePicker
    fun plus(module: StepsModule): StepsComponent

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun appContext(@App context: Context): Builder

        @BindsInstance
        fun activity(activity: Activity): Builder

        @BindsInstance
        fun horse(service: HorseService): Builder

        @BindsInstance
        fun img(loader: ImageLoader): Builder

        fun module(module: CreatorModule): Builder
        fun build(): CreatorComponent
    }
}