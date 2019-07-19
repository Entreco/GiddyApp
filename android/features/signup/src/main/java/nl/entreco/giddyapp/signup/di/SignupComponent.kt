package nl.entreco.giddyapp.signup.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import nl.entreco.giddyapp.libauth.Authenticator
import nl.entreco.giddyapp.libcore.di.AppContext
import nl.entreco.giddyapp.signup.SignupViewModel

@Component(modules = [SignupModule::class])
interface SignupComponent {

    fun viewModel() : SignupViewModel

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun appContext(@AppContext context: Context): Builder

        @BindsInstance
        fun authenticator(authenticator: Authenticator): Builder

        fun build(): SignupComponent
    }
}