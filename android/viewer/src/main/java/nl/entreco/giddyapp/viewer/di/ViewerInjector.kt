package nl.entreco.giddyapp.viewer.di

import nl.entreco.giddyapp.GiddyApp
import nl.entreco.giddyapp.core.ApiModule
import nl.entreco.giddyapp.viewer.ViewerActivity

internal object ViewerInjector {

    inline fun ViewerActivity.fromModule(
        mode: LazyThreadSafetyMode = LazyThreadSafetyMode.NONE,
        crossinline provider: ()-> ViewerModule
    ) : Lazy<ViewerComponent> = lazy(mode) {
        val featureComponent = (applicationContext as GiddyApp).get()
        val apiComponent = featureComponent.plus(ApiModule())
        DaggerViewerComponent.builder()
            .apiComponent(apiComponent)
            .viewerModule(provider())
            .build()
    }
}