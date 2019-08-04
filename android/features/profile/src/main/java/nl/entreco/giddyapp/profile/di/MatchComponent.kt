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
import nl.entreco.giddyapp.profile.matches.MatchActivity
import nl.entreco.giddyapp.profile.matches.MatchViewModel
import nl.entreco.giddyapp.profile.matches.details.MatchDetailViewModel

@Component(modules = [MatchModule::class])
interface MatchComponent {

    fun viewModel(): MatchViewModel
    fun loader(): ImageLoader
    fun detailViewModel(): MatchDetailViewModel

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun appContext(@AppContext context: Context): Builder

        @BindsInstance
        fun activity(activity: MatchActivity): Builder

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

        fun build(): MatchComponent
    }
}