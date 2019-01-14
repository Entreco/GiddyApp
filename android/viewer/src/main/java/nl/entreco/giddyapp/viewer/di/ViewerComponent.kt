package nl.entreco.giddyapp.viewer.di

import dagger.Component
import nl.entreco.giddyapp.core.api.ApiComponent
import nl.entreco.giddyapp.core.images.ImageComponent
import nl.entreco.giddyapp.viewer.ViewerViewModel

@Component(modules = [ViewerModule::class], dependencies = [ApiComponent::class, ImageComponent::class])
interface ViewerComponent {
    fun viewModel(): ViewerViewModel
    fun plus(module: SwipeModule): SwipeComponent
}