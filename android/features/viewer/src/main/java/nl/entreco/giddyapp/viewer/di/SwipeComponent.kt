package nl.entreco.giddyapp.viewer.di

import dagger.Subcomponent
import nl.entreco.giddyapp.libimg.loader.ImageLoader
import nl.entreco.giddyapp.viewer.ui.swiper.SwipeViewModel

@Subcomponent(modules = [SwipeModule::class])
interface SwipeComponent {
    fun viewModel(): SwipeViewModel
    fun loader(): ImageLoader
}