package nl.entreco.giddyapp.libs.horses.di

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import nl.entreco.giddyapp.core.di.AppScope
import nl.entreco.giddyapp.libs.horses.HorseService
import nl.entreco.giddyapp.libs.horses.data.FbHorseService

@Module
object HorseModule {

    @Provides
    @JvmStatic
    internal fun provideFireStore(): FirebaseFirestore {
        Log.i("DEPINJ", "provide FireStore")
        val fireStore = FirebaseFirestore.getInstance()
        val settings = FirebaseFirestoreSettings.Builder()
            .setTimestampsInSnapshotsEnabled(true)
            .build()
        fireStore.firestoreSettings = settings
        return fireStore
    }

    @Provides
    @JvmStatic
    internal fun provideFireStorage(): FirebaseStorage {
        Log.i("DEPINJ", "provide FireStorage")
        return FirebaseStorage.getInstance()
    }

    @Provides
    @JvmStatic
    @AppScope
    internal fun provideFbHorseService(db: FirebaseFirestore, storage: FirebaseStorage): HorseService {
        Log.i("DEPINJ", "provide FbHorseService")
        return FbHorseService(db, storage)
    }
}