package nl.entreco.giddyapp.libhorses.di

import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
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
    internal fun provideFireStore(fbApp: FirebaseApp): FirebaseFirestore {
        val fireStore = FirebaseFirestore.getInstance(fbApp)
        val settings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build()
        fireStore.firestoreSettings = settings
        return fireStore
    }

    @Provides
    @JvmStatic
    internal fun provideFireStorage(fbApp: FirebaseApp): FirebaseStorage {
        return FirebaseStorage.getInstance(fbApp)
    }

    @Provides
    @JvmStatic
    @AppScope
    internal fun provideFbHorseService(db: FirebaseFirestore, storage: FirebaseStorage): HorseService {
        return FbHorseService(db, storage)
    }
}