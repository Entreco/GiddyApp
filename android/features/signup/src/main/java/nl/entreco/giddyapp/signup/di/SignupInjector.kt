package nl.entreco.giddyapp.signup.di

import nl.entreco.giddyapp.featureComponent
import nl.entreco.giddyapp.signup.SignupActivity

internal object SignupInjector {

    inline fun SignupActivity.fromModule(
        mode: LazyThreadSafetyMode = LazyThreadSafetyMode.NONE,
        crossinline provider: () -> SignupModule
    ): Lazy<SignupComponent> = lazy {
        val component = featureComponent()
        DaggerSignupComponent.builder()
            .appContext(component.appContext())
            .authenticator(component.authentication())
            .build()
    }
}