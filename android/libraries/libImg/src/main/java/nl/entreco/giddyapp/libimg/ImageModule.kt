package nl.entreco.giddyapp.libimg

import android.util.DisplayMetrics
import dagger.Module
import dagger.Provides
import nl.entreco.giddyapp.libimg.loader.ImageLoader
import nl.entreco.giddyapp.libimg.loader.glide.GlideImageLoader
import javax.inject.Inject

@Module
class ImageModule @Inject constructor(private val metrics: DisplayMetrics) {
    @Provides
    fun provideImageLoader(): ImageLoader {
        return GlideImageLoader(metrics.widthPixels, metrics.heightPixels)
    }
}