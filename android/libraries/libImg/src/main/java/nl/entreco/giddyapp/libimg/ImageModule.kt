package nl.entreco.giddyapp.libimg

import android.app.Activity
import android.util.DisplayMetrics
import dagger.Module
import dagger.Provides
import nl.entreco.giddyapp.libimg.loader.glide.GlideImageLoader
import nl.entreco.giddyapp.libimg.loader.ImageLoader
import nl.entreco.giddyapp.libimg.picker.ImagePicker
import nl.entreco.giddyapp.libimg.picker.ThirdPartyImagePicker
import javax.inject.Inject

@Module
class ImageModule @Inject constructor(private val activity: Activity, private val metrics: DisplayMetrics) {
    @Provides
    fun provideImageLoader(): ImageLoader {
        return GlideImageLoader(metrics.widthPixels, metrics.heightPixels)
    }

    @Provides
    fun provideImagePicker(): ImagePicker {
        return ThirdPartyImagePicker(activity)
    }
}