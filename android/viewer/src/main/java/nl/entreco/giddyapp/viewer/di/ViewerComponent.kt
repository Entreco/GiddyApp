package nl.entreco.giddyapp.viewer.di

import android.content.Context
import dagger.Component
import nl.entreco.giddyapp.core.ApiComponent
import nl.entreco.giddyapp.core.FeatureScope
import nl.entreco.giddyapp.viewer.ViewerViewModel

@Component(modules = [ViewerModule::class], dependencies = [ApiComponent::class])
@FeatureScope
interface ViewerComponent {
    fun context(): Context
    fun viewModel(): ViewerViewModel
    fun plus(module: SwipeModule): SwipeComponent
}