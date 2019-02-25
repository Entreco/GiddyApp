package nl.entreco.giddyapp.creator.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import nl.entreco.giddyapp.creator.CreatorViewModel
import nl.entreco.giddyapp.libimg.ImageModule
import nl.entreco.giddyapp.libpicker.ImagePicker
import nl.entreco.giddyapp.libpicker.PickerModule
import nl.entreco.giddyapp.libs.horses.HorseModule

@Component(modules = [CreatorModule::class, HorseModule::class, ImageModule::class, PickerModule::class])
interface CreatorComponent {
    fun viewModel(): CreatorViewModel
    fun picker(): ImagePicker

    fun plus(module: StepsModule) : StepsComponent

    @Component.Builder
    interface Builder {
        @BindsInstance fun context(context: Context) : Builder
        fun creatorModule(module: CreatorModule) : Builder
        fun horseModule(module: HorseModule): Builder
        fun imageModule(module: ImageModule): Builder
        fun pickerModule(module: PickerModule): Builder
        fun build(): CreatorComponent
    }
}