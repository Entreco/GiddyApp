package nl.entreco.giddyapp.creator.di

import dagger.Component
import nl.entreco.giddyapp.core.horse.HorseModule
import nl.entreco.giddyapp.core.images.ImageModule
import nl.entreco.giddyapp.core.images.ImagePicker
import nl.entreco.giddyapp.creator.CreatorViewModel

@Component(modules = [CreatorModule::class, HorseModule::class, ImageModule::class])
interface CreatorComponent {
    fun viewModel(): CreatorViewModel
    fun picker(): ImagePicker

    @Component.Builder
    interface Builder {
        fun creatorModule(module: CreatorModule) : Builder
        fun horseModule(module: HorseModule): Builder
        fun imageModule(module: ImageModule): Builder
        fun build(): CreatorComponent
    }
}