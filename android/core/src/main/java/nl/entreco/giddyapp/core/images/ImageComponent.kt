package nl.entreco.giddyapp.core.images

import dagger.Subcomponent

@Subcomponent(modules = [ImageModule::class])
interface ImageComponent {
    fun cache(): ImageCache
    fun loader(): ImageLoader
}