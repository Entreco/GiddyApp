package nl.entreco.giddyapp.viewer.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import nl.entreco.giddyapp.core.images.ImageModule
import nl.entreco.giddyapp.viewer.ViewerViewModel
import nl.entreco.giddyapp.core.ui.DetailSheet
import nl.entreco.giddyapp.libs.horses.di.HorseModule

@Component(modules = [ViewerModule::class, HorseModule::class, ImageModule::class])
interface ViewerComponent {
    fun viewModel(): ViewerViewModel
    fun sheet(): DetailSheet
    fun plus(module: SwipeModule): SwipeComponent

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        fun viewerModule(module: ViewerModule): Builder
        fun horseModule(module: HorseModule): Builder
        fun imageModule(module: ImageModule): Builder
        fun build(): ViewerComponent
    }
}