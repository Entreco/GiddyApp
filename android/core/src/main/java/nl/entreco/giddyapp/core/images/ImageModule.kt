package nl.entreco.giddyapp.core.images

import dagger.Module
import dagger.Provides

@Module
class ImageModule {
    @Provides
    fun provideImageLoader(): ImageLoader {
        return GlideImageLoader()
    }
}