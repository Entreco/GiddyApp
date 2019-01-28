package nl.entreco.giddyapp.creator.di

import nl.entreco.giddyapp.core.horse.HorseModule
import nl.entreco.giddyapp.core.images.ImageModule
import nl.entreco.giddyapp.creator.CreatorActivity

internal object CreatorInjector {

    inline fun CreatorActivity.fromModule(
        mode: LazyThreadSafetyMode = kotlin.LazyThreadSafetyMode.NONE,
        crossinline provider: () -> CreatorModule
    ): Lazy<CreatorComponent> = kotlin.lazy(mode) {
        DaggerCreatorComponent.builder()
            .creatorModule(provider())
            .horseModule(HorseModule())
            .imageModule(ImageModule(this, resources.displayMetrics))
            .build()
    }
}