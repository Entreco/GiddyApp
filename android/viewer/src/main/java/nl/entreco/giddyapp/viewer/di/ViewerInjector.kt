package nl.entreco.giddyapp.viewer.di

import androidx.fragment.app.Fragment
import nl.entreco.giddyapp.GiddyApp
import nl.entreco.giddyapp.core.api.ApiModule
import nl.entreco.giddyapp.core.ComponentProvider
import nl.entreco.giddyapp.core.images.ImageModule
import nl.entreco.giddyapp.viewer.ViewerActivity

internal object ViewerInjector {

    inline fun ViewerActivity.fromModule(
        mode: LazyThreadSafetyMode = LazyThreadSafetyMode.NONE,
        crossinline provider: () -> ViewerModule
    ): Lazy<ViewerComponent> = lazy(mode) {
        val featureComponent = (applicationContext as GiddyApp).get()
        val apiComponent = featureComponent.plus(ApiModule())
        val imageComponent = featureComponent.plus(ImageModule())
        DaggerViewerComponent.builder()
            .apiComponent(apiComponent)
            .imageComponent(imageComponent)
            .viewerModule(provider())
            .build()
    }

    inline fun Fragment.fromActivity(
        mode: LazyThreadSafetyMode = LazyThreadSafetyMode.NONE,
        crossinline provider: () -> SwipeModule
    ): Lazy<SwipeComponent> = lazy(mode) {
        val componentProvider = activity as? ComponentProvider<ViewerComponent> ?: throw IllegalStateException("activity($activity) must implement ComponentProvider<ViewerComponent>")
        val component = componentProvider.get()
        component.plus(provider())
    }
}