package nl.entreco.giddyapp.core.images

import android.util.DisplayMetrics
import dagger.Module
import dagger.Provides
import javax.inject.Inject

@Module
class ImageModule @Inject constructor(private val metrics: DisplayMetrics) {
    @Provides
    fun provideImageLoader(): ImageLoader {
        return GlideImageLoader(metrics.widthPixels, metrics.heightPixels)
    }
}