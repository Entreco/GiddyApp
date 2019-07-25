package nl.entreco.giddyapp.profile.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import nl.entreco.giddyapp.libauth.Authenticator
import nl.entreco.giddyapp.libauth.UserService
import nl.entreco.giddyapp.libcore.di.AppContext
import nl.entreco.giddyapp.libhorses.HorseService
import nl.entreco.giddyapp.libimg.loader.ImageLoader
import nl.entreco.giddyapp.libmatches.MatchService
import nl.entreco.giddyapp.profile.ProfileActivity
import nl.entreco.giddyapp.profile.ProfileNavigation
import nl.entreco.giddyapp.profile.ProfileViewModel
import nl.entreco.giddyapp.profile.matches.MatchesViewModel
import nl.entreco.giddyapp.profile.header.ProfileHeaderViewModel

@Component(modules = [ProfileModule::class])
interface ProfileComponent {

    fun viewModel(): ProfileViewModel
    fun header(): ProfileHeaderViewModel
    fun matches(): MatchesViewModel
    fun navigation(): ProfileNavigation
    fun auth(): Authenticator
    fun loader(): ImageLoader

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun appContext(@AppContext context: Context): Builder

        @BindsInstance
        fun activity(activity: ProfileActivity): Builder

        @BindsInstance
        fun auth(auth: Authenticator): Builder

        @BindsInstance
        fun user(service: UserService): Builder

        @BindsInstance
        fun horse(service: HorseService): Builder

        @BindsInstance
        fun match(service: MatchService): Builder

        @BindsInstance
        fun imageLoader(loader: ImageLoader): Builder

        fun build(): ProfileComponent
    }
}