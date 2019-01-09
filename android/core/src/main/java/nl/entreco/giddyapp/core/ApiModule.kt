package nl.entreco.giddyapp.core

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides

@Module
class ApiModule {
    @Provides
    fun provideApi(context: Context): Api {
        Log.i("DAGGER", "provideApi")
        return Api(context)
    }

    @Provides
    fun provideFireStore(): FirebaseFirestore {
        Log.i("DAGGER", "provideFireStore")
        return FirebaseFirestore.getInstance()
    }

    @Provides
    fun provideFireStorage(): FirebaseStorage {
        Log.i("DAGGER", "provideFireStorage")
        return FirebaseStorage.getInstance()
    }
}