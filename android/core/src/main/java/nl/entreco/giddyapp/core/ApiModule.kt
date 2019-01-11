package nl.entreco.giddyapp.core

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides


@Module
class ApiModule {

    @Provides
    @ApiScope
    fun provideImageCache(): ImageCache {
        Log.i("DAGGER", "provideImageCache")
        return ImageCache()
    }

    @Provides
    @ApiScope
    fun provideApi(context: Context): Api {
        Log.i("DAGGER", "provideApi")
        return Api(context)
    }

    @Provides
    @ApiScope
    fun provideFireStore(): FirebaseFirestore {
        Log.i("DAGGER", "provideFireStore")
        val fireStore = FirebaseFirestore.getInstance()
        val settings = FirebaseFirestoreSettings.Builder()
            .setTimestampsInSnapshotsEnabled(true)
            .build()
        fireStore.firestoreSettings = settings
        return fireStore
    }

    @Provides
    @ApiScope
    fun provideFireStorage(): FirebaseStorage {
        Log.i("DAGGER", "provideFireStorage")
        return FirebaseStorage.getInstance()
    }
}