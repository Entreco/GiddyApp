package nl.entreco.giddyapp.viewer.di

import android.app.Activity
import android.util.DisplayMetrics
import android.view.Window
import dagger.BindsInstance
import dagger.Component
import nl.entreco.giddyapp.libcore.ui.DetailSheet
import nl.entreco.giddyapp.libimg.loader.ImageLoader
import nl.entreco.giddyapp.libhorses.HorseService
import nl.entreco.giddyapp.viewer.ViewerViewModel
import nl.entreco.giddyapp.viewer.navigation.ViewerNavigation
import nl.entreco.giddyapp.viewer.ui.ToolbarAnimator

@Component(modules = [ViewerModule::class])
interface ViewerComponent {
    fun viewModel(): ViewerViewModel
    fun sheet(): DetailSheet
    fun navigation(): ViewerNavigation
    fun animator(): ToolbarAnimator
    fun plus(module: SwipeModule): SwipeComponent

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun activity(activity: Activity): Builder

        @BindsInstance
        fun metrics(metrics: DisplayMetrics): Builder

        @BindsInstance
        fun window(window: Window): Builder

        @BindsInstance
        fun horse(service: HorseService): Builder

        @BindsInstance
        fun img(loader: ImageLoader): Builder

        fun module(module: ViewerModule): Builder
        fun build(): ViewerComponent
    }
}