package nl.entreco.giddyapp.viewer

import nl.entreco.giddyapp.core.FeatureComponent

interface ViewerComponent: FeatureComponent {
    fun viewModel(): ViewerViewModel
}