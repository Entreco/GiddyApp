package nl.entreco.giddyapp.lib.img.di

import android.util.DisplayMetrics
import android.util.Log
import dagger.Module
import dagger.Provides
import nl.entreco.giddyapp.core.di.AppScope
import nl.entreco.giddyapp.lib.img.loader.ImageLoader
import nl.entreco.giddyapp.lib.img.loader.glide.GlideImageLoader

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