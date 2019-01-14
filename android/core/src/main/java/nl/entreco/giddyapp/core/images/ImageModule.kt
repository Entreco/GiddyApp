package nl.entreco.giddyapp.core.images

import android.util.Log
import dagger.Module
import dagger.Provides

@Module
class ImageModule {
    @Provides
    fun provideImageCache(): ImageCache {
        Log.i("DAGGER", "provideImageCache")
        return ImageCache()
    }

    @Provides
    fun provideImageLoader() : ImageLoader {
        return GlideImageLoader()
    }
}