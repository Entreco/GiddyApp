package nl.entreco.giddyapp.viewer.di

import androidx.fragment.app.Fragment
import nl.entreco.giddyapp.libcore.di.DiProvider
import nl.entreco.giddyapp.featureComponent
import nl.entreco.giddyapp.viewer.ViewerActivity

internal object ViewerInjector {

    inline fun ViewerActivity.fromModule(
        mode: LazyThreadSafetyMode = LazyThreadSafetyMode.NONE,
        crossinline provider: () -> ViewerModule
    ): Lazy<ViewerComponent> = lazy(mode) {

        DaggerViewerComponent.builder()
            .activity(this)
            .window(this.window)
            .metrics(featureComponent().metrics())
            .horse(featureComponent().horseService())
            .img(featureComponent().imageLoader())
            .module(provider())
            .build()
    }

    inline fun Fragment.fromActivity(
        mode: LazyThreadSafetyMode = LazyThreadSafetyMode.NONE,
        crossinline provider: () -> SwipeModule
    ): Lazy<SwipeComponent> = lazy(mode) {
        val componentProvider = activity as? DiProvider<ViewerComponent>
            ?: throw IllegalStateException("activity($activity) must implement ComponentProvider<ViewerComponent>")
        val component = componentProvider.get()
        component.plus(provider())
    }
}