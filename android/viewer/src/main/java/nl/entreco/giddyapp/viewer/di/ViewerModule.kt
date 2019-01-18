package nl.entreco.giddyapp.viewer.di

import android.content.Context
import android.content.res.Resources
import android.media.SoundPool
import android.view.View
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import nl.entreco.giddyapp.viewer.data.FbHorseService
import nl.entreco.giddyapp.viewer.data.SoundPoolService
import nl.entreco.giddyapp.viewer.domain.HorseService
import nl.entreco.giddyapp.viewer.domain.sound.SoundService

@Module
class ViewerModule(private val url: String?, private val sheet: View) {

    @Provides
    fun provideUrl(): String = url ?: "nope"

    @Provides
    fun provideSheet() : View {
        return sheet
    }

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