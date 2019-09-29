package nl.entreco.giddyapp.history.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import nl.entreco.giddyapp.history.HistoryActivity
import nl.entreco.giddyapp.history.HistoryHeaderViewModel
import nl.entreco.giddyapp.history.HistoryViewModel
import nl.entreco.giddyapp.libauth.Authenticator
import nl.entreco.giddyapp.libauth.UserService
import nl.entreco.giddyapp.libcore.di.AppContext
import nl.entreco.giddyapp.libhorses.HorseService
import nl.entreco.giddyapp.libimg.loader.ImageLoader
import nl.entreco.giddyapp.libmatches.MatchService
import nl.entreco.giddyapp.history.details.MatchDetailAdapter
import nl.entreco.giddyapp.history.details.MatchDetailViewModel

@Component(modules = [HistoryModule::class])
interface HistoryComponent {

    fun viewModel(): HistoryViewModel
    fun header(): HistoryHeaderViewModel
    fun loader(): ImageLoader
    fun adapter(): MatchDetailAdapter
    fun detailViewModel(): MatchDetailViewModel

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun appContext(@AppContext context: Context): Builder

        @BindsInstance
        fun activity(activity: HistoryActivity): Builder

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

        fun build(): HistoryComponent
    }
}