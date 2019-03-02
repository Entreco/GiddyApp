package nl.entreco.giddyapp.libimg.di

import android.util.DisplayMetrics
import android.util.Log
import dagger.Module
import dagger.Provides
import nl.entreco.giddyapp.libcore.di.AppScope
import nl.entreco.giddyapp.libimg.loader.ImageLoader
import nl.entreco.giddyapp.libimg.loader.glide.GlideImageLoader

@Module
object ImageModule {

    @Provides
    @JvmStatic
    @AppScope
    fun provideImageLoader(metrics: DisplayMetrics): ImageLoader {
        Log.i("DEPINJ", "provide Glide")
        return GlideImageLoader(metrics.widthPixels, metrics.heightPixels)
    }
}