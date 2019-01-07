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
        return FirebaseFirestore.getInstance()
    }

    @Provides
    fun provideFireStorage(): FirebaseStorage {
        return FirebaseStorage.getInstance()
    }
}