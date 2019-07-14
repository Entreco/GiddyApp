package nl.entreco.giddyapp.creator.di

import android.view.View
import nl.entreco.giddyapp.creator.CreatorActivity
import nl.entreco.giddyapp.creator.ui.CreateStepFragment
import nl.entreco.giddyapp.featureComponent
import nl.entreco.giddyapp.libcore.di.DiProvider

internal object CreatorInjector {

    inline fun CreatorActivity.fromModule(
        mode: LazyThreadSafetyMode = LazyThreadSafetyMode.NONE,
        crossinline provider: () -> CreatorModule
    ): Lazy<CreatorComponent> = kotlin.lazy(mode) {
        val component = featureComponent()
        DaggerCreatorComponent.builder()
            .appContext(component.appContext())
            .activity(this)
            .manager(this.supportFragmentManager)
            .horse(featureComponent().horseService())
            .img(featureComponent().imageLoader())
            .fbApp(featureComponent().fbApp())
            .auth(featureComponent().authentication())
            .module(provider())
            .build()
    }

    inline fun CreateStepFragment.componentFromSheet(
        mode: LazyThreadSafetyMode = LazyThreadSafetyMode.NONE,
        crossinline provider: () -> View
    ): Lazy<StepsComponent> = lazy(mode) {
        val componentProvider = activity as? DiProvider<CreatorComponent>
            ?: throw IllegalStateException("activity($activity) must implement ComponentProvider<CreatorComponent>")
        val component = componentProvider.get()
        val stepsModule = StepsModule(provider(), parentViewModel.state().value)
        component.plus(stepsModule)
    }
}