package nl.entreco.giddyapp.viewer

import android.content.Context
import android.content.res.Resources
import dagger.Module
import dagger.Provides

@Module
class ViewerModule(private val url: String?) {

    @Provides
    fun provideUrl(): String = url ?: "nope"

    @Provides
    fun provideResources(context: Context): Resources {
        return context.resources
    }
}