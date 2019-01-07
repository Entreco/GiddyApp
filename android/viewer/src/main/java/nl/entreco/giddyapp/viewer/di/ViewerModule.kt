package nl.entreco.giddyapp.viewer.di

import android.content.Context
import android.content.res.Resources
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import nl.entreco.giddyapp.viewer.HorseService
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
    fun provideFbHorseService(db: FirebaseFirestore, storage: FirebaseStorage) : HorseService{
        return FbHorseService(db, storage)
    }
}