package nl.entreco.giddyapp.profile.di

import nl.entreco.giddyapp.featureComponent
import nl.entreco.giddyapp.profile.ProfileActivity

internal object ProfileInjector {
    inline fun ProfileActivity.fromModule(
        mode: LazyThreadSafetyMode = LazyThreadSafetyMode.NONE,
        crossinline provider: () -> ProfileModule
    ): Lazy<ProfileComponent> = lazy {
        val component = featureComponent()
        DaggerProfileComponent.builder()
            .appContext(component.appContext())
            .activity(this)
            .auth(component.authentication())
            .imageLoader(component.imageLoader())
            .horse(component.horseService())
            .user(component.userService())
            .match(component.matchService())
            .build()
    }
}