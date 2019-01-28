package nl.entreco.giddyapp.core.horse

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import nl.entreco.giddyapp.core.horse.data.FbHorseService
import nl.entreco.giddyapp.libs.horses.HorseService

@Module
class HorseModule {

    @Provides
    fun provideFireStore(): FirebaseFirestore {
        val fireStore = FirebaseFirestore.getInstance()
        val settings = FirebaseFirestoreSettings.Builder()
            .setTimestampsInSnapshotsEnabled(true)
            .build()
        fireStore.firestoreSettings = settings
        return fireStore
    }

    @Provides
    fun provideFireStorage(): FirebaseStorage {
        return FirebaseStorage.getInstance()
    }

    @Provides
    fun provideFbHorseService(db: FirebaseFirestore, storage: FirebaseStorage): HorseService {
        return FbHorseService(db, storage)
    }
}