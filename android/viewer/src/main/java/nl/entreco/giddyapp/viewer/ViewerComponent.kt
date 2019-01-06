package nl.entreco.giddyapp.viewer

import dagger.Component
import nl.entreco.giddyapp.core.ApiComponent
import nl.entreco.giddyapp.core.FeatureScope

@Component(modules = [ViewerModule::class], dependencies = [ApiComponent::class])
@FeatureScope
interface ViewerComponent {
    fun viewModel(): ViewerViewModel
}