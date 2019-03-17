package nl.entreco.giddyapp.libsound.di

import android.content.Context
import android.media.SoundPool
import dagger.Module
import dagger.Provides
import nl.entreco.giddyapp.libcore.di.ActivityContext
import nl.entreco.giddyapp.libsound.SoundService
import nl.entreco.giddyapp.libsound.data.SoundPoolService

@Module
object SoundModule {

    @Provides
    @JvmStatic
    fun provideSoundService(@ActivityContext context: Context): SoundService {
        val pool = SoundPool.Builder().setMaxStreams(2).build()
        return SoundPoolService(context, pool)
    }
}