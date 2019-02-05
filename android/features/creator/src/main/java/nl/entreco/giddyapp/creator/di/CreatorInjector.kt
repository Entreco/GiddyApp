package nl.entreco.giddyapp.creator.di

import androidx.fragment.app.Fragment
import nl.entreco.giddyapp.core.ComponentProvider
import nl.entreco.giddyapp.libimg.ImageModule
import nl.entreco.giddyapp.creator.CreatorActivity
import nl.entreco.giddyapp.libpicker.PickerModule
import nl.entreco.giddyapp.libs.horses.HorseModule

internal object CreatorInjector {

    inline fun CreatorActivity.fromModule(
        mode: LazyThreadSafetyMode = kotlin.LazyThreadSafetyMode.NONE,
        crossinline provider: () -> CreatorModule
    ): Lazy<CreatorComponent> = kotlin.lazy(mode) {
        DaggerCreatorComponent.builder()
            .creatorModule(provider())
            .horseModule(HorseModule())
            .imageModule(ImageModule(resources.displayMetrics))
            .pickerModule(PickerModule(this))
            .build()
    }

    inline fun Fragment.fromActivity(
        mode: LazyThreadSafetyMode = LazyThreadSafetyMode.NONE,
        crossinline provider: () -> StepsModule
    ): Lazy<StepsComponent> = lazy(mode) {
        val componentProvider = activity as? ComponentProvider<CreatorComponent>
            ?: throw IllegalStateException("activity($activity) must implement ComponentProvider<CreatorComponent>")
        val component = componentProvider.get()
        component.plus(provider())
    }
}