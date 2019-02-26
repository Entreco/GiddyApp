package nl.entreco.giddyapp.viewer.di

import android.app.Activity
import android.content.Context
import android.view.Window
import dagger.BindsInstance
import dagger.Component
import nl.entreco.giddyapp.libimg.ImageModule
import nl.entreco.giddyapp.viewer.ViewerViewModel
import nl.entreco.giddyapp.core.ui.DetailSheet
import nl.entreco.giddyapp.libs.horses.HorseModule
import nl.entreco.giddyapp.viewer.navigation.ViewerNavigation
import nl.entreco.giddyapp.viewer.ui.ToolbarAnimator

@Component(modules = [ViewerModule::class, HorseModule::class, ImageModule::class])
interface ViewerComponent {
    fun viewModel(): ViewerViewModel
    fun sheet(): DetailSheet
    fun navigation(): ViewerNavigation
    fun animator(): ToolbarAnimator
    fun plus(module: SwipeModule): SwipeComponent

    @Component.Builder
    interface Builder {
        @BindsInstance fun activity(activity: Activity): Builder
        @BindsInstance fun window(window: Window) : Builder

        fun viewerModule(module: ViewerModule): Builder
        fun horseModule(module: HorseModule): Builder
        fun imageModule(module: ImageModule): Builder
        fun build(): ViewerComponent
    }
}