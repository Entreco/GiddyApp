package nl.entreco.giddyapp.creator.di

import android.view.View
import androidx.fragment.app.Fragment
import nl.entreco.giddyapp.core.ComponentProvider
import nl.entreco.giddyapp.libimg.ImageModule
import nl.entreco.giddyapp.creator.CreatorActivity
import nl.entreco.giddyapp.creator.ui.CreateStepFragment
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

    inline fun CreateStepFragment.componentFromSheet(
        mode: LazyThreadSafetyMode = LazyThreadSafetyMode.NONE,
        crossinline provider: () -> View
    ): Lazy<StepsComponent> = lazy(mode) {
        val componentProvider = activity as? ComponentProvider<CreatorComponent>
            ?: throw IllegalStateException("activity($activity) must implement ComponentProvider<CreatorComponent>")
        val component = componentProvider.get()
        val stepsModule = StepsModule(provider(), parentViewModel.state().value)
        component.plus(stepsModule)
    }
}