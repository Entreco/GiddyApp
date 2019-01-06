package nl.entreco.giddyapp.viewer

import nl.entreco.giddyapp.core.FeatureModule

class ViewerModule(private val url: String?) : FeatureModule<ViewerComponent> {
    private fun provideUrl(): String? {
        return url
    }

    override fun create(): ViewerComponent {
        return object : ViewerComponent {
            override fun viewModel(): ViewerViewModel {
                return ViewerViewModel(provideUrl())
            }
        }
    }
}