package nl.entreco.giddyapp.profile.di

import android.app.Activity
import android.content.Context
import dagger.BindsInstance
import dagger.Component
import nl.entreco.giddyapp.libcore.di.AppContext
import nl.entreco.giddyapp.libcore.launch.DynamicLauncher
import nl.entreco.giddyapp.profile.ProfileNavigation
import nl.entreco.giddyapp.profile.ProfileViewModel

@Component(modules = [ProfileModule::class])
interface ProfileComponent {

    fun viewModel(): ProfileViewModel
    fun navigation(): ProfileNavigation

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun appContext(@AppContext context: Context): Builder

        @BindsInstance
        fun activity(activity: Activity): Builder

        @BindsInstance
        fun dynamicLauncher(launcher: DynamicLauncher): Builder

        fun module(module: ProfileModule): Builder
        fun build(): ProfileComponent
    }
}