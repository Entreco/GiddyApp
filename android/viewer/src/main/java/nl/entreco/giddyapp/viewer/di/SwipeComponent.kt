package nl.entreco.giddyapp.viewer.di

import dagger.Subcomponent
import nl.entreco.giddyapp.viewer.swiper.SwipeViewModel

@Subcomponent(modules = [SwipeModule::class])
interface SwipeComponent {
    fun viewModel(): SwipeViewModel
}