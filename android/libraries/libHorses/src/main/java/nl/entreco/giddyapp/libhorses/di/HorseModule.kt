package nl.entreco.giddyapp.libhorses.di

import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import nl.entreco.giddyapp.libcore.di.AppScope
import nl.entreco.giddyapp.libhorses.HorseService
import nl.entreco.giddyapp.libhorses.data.FbHorseService

@Module
object HorseModule {

    @Provides
    @JvmStatic
    @AppScope
    internal fun provideFbHorseService(): HorseService {
        val app = FirebaseApp.getInstance()
        val db = FirebaseFirestore.getInstance()
        val storage = FirebaseStorage.getInstance()
        return FbHorseService(db, storage)
    }
}