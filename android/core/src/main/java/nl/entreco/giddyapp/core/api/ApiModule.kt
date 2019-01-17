package nl.entreco.giddyapp.core.api

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides


@Module
class ApiModule {

    @Provides
    fun provideApi(context: Context): Api {
        return Api(context)
    }

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
}