package nl.entreco.giddyapp.core.images

import android.util.DisplayMetrics
import dagger.Module
import dagger.Provides

@Module
class ImageModule(private val metrics: DisplayMetrics) {
    @Provides
    fun provideImageLoader(): ImageLoader {
        return GlideImageLoader(metrics.widthPixels, metrics.heightPixels)
    }
}