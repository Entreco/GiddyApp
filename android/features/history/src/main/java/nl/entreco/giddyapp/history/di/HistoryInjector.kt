package nl.entreco.giddyapp.history.di

import nl.entreco.giddyapp.featureComponent
import nl.entreco.giddyapp.history.HistoryActivity

internal object HistoryInjector {

    inline fun HistoryActivity.fromModule(
        mode: LazyThreadSafetyMode = LazyThreadSafetyMode.NONE,
        crossinline provider: () -> HistoryModule
    ): Lazy<HistoryComponent> = lazy {
        val component = featureComponent()
        DaggerHistoryComponent.builder()
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