package nl.entreco.giddyapp.viewer.di

import androidx.fragment.app.Fragment
import nl.entreco.giddyapp.core.ComponentProvider
import nl.entreco.giddyapp.libs.horses.HorseModule
import nl.entreco.giddyapp.libimg.ImageModule
import nl.entreco.giddyapp.viewer.ViewerActivity

internal object ViewerInjector {

    inline fun ViewerActivity.fromModule(
        mode: LazyThreadSafetyMode = LazyThreadSafetyMode.NONE,
        crossinline provider: () -> ViewerModule
    ): Lazy<ViewerComponent> = lazy(mode) {

        DaggerViewerComponent.builder()
            .context(this)
            .horseModule(HorseModule())
            .imageModule(ImageModule(this, resources.displayMetrics))
            .viewerModule(provider())
            .build()
    }

    inline fun Fragment.fromActivity(
        mode: LazyThreadSafetyMode = LazyThreadSafetyMode.NONE,
        crossinline provider: () -> SwipeModule
    ): Lazy<SwipeComponent> = lazy(mode) {
        val componentProvider = activity as? ComponentProvider<ViewerComponent>
            ?: throw IllegalStateException("activity($activity) must implement ComponentProvider<ViewerComponent>")
        val component = componentProvider.get()
        component.plus(provider())
    }
}