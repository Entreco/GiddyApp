package nl.entreco.giddyapp.viewer.di

import android.content.Context
import android.util.DisplayMetrics
import android.view.Window
import dagger.BindsInstance
import dagger.Component
import nl.entreco.giddyapp.libauth.Authenticator
import nl.entreco.giddyapp.libauth.UserService
import nl.entreco.giddyapp.libcore.di.AppContext
import nl.entreco.giddyapp.libcore.launch.DynamicLauncher
import nl.entreco.giddyapp.libcore.ui.DetailSheet
import nl.entreco.giddyapp.libhorses.HorseService
import nl.entreco.giddyapp.libimg.loader.ImageLoader
import nl.entreco.giddyapp.libsound.di.SoundModule
import nl.entreco.giddyapp.viewer.ViewerActivity
import nl.entreco.giddyapp.viewer.ViewerViewModel
import nl.entreco.giddyapp.viewer.navigation.ViewerNavigation
import nl.entreco.giddyapp.viewer.ui.ToolbarAnimator
import nl.entreco.giddyapp.viewer.ui.filter.SearchPanelBehavior

@ViewerScope
@Component(modules = [ViewerModule::class, SoundModule::class])
interface ViewerComponent {
    fun viewModel(): ViewerViewModel
    fun sheet(): DetailSheet
    fun filter(): SearchPanelBehavior
    fun navigation(): ViewerNavigation
    fun animator(): ToolbarAnimator
    fun loader(): ImageLoader
    fun plus(module: SwipeModule): SwipeComponent

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun appContext(@AppContext context: Context): Builder

        @BindsInstance
        fun activity(activity: ViewerActivity): Builder

        @BindsInstance
        fun metrics(metrics: DisplayMetrics): Builder

        @BindsInstance
        fun window(window: Window): Builder

        @BindsInstance
        fun horse(service: HorseService): Builder

        @BindsInstance
        fun user(service: UserService): Builder

        @BindsInstance
        fun img(loader: ImageLoader): Builder

        @BindsInstance
        fun auth(auth: Authenticator): Builder

        @BindsInstance
        fun dynamicLauncher(launcher: DynamicLauncher): Builder

        fun module(module: ViewerModule): Builder
        fun build(): ViewerComponent
    }
}