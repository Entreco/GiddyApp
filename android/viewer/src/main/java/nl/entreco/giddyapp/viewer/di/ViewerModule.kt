package nl.entreco.giddyapp.viewer.di

import android.content.Context
import android.content.res.Resources
import android.media.SoundPool
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import nl.entreco.giddyapp.viewer.HorseService
import nl.entreco.giddyapp.viewer.SoundPoolService
import nl.entreco.giddyapp.viewer.SoundService
import nl.entreco.giddyapp.viewer.data.FbHorseService

@Module
class ViewerModule(private val url: String?) {

    @Provides
    fun provideUrl(): String = url ?: "nope"

    @Provides
    fun provideResources(context: Context): Resources {
        return context.resources
    }

    @Provides
    fun provideFbHorseService(db: FirebaseFirestore, storage: FirebaseStorage): HorseService {
        return FbHorseService(db, storage)
    }

    @Provides
    fun provideSoundService(context: Context): SoundService {
        val pool = SoundPool.Builder().setMaxStreams(2).build()
        return SoundPoolService(context, pool)
    }
}