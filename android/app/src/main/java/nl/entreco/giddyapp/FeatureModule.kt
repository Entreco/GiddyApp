package nl.entreco.giddyapp

import android.app.Application
import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import nl.entreco.giddyapp.libcore.di.AppContext
import nl.entreco.giddyapp.libcore.di.AppScope

@Module
object FeatureModule {

    @AppContext
    @Provides
    @JvmStatic
    @AppScope
    fun provideContext(app: Application): Context {
        return app
    }

    @Provides
    @JvmStatic
    @AppScope
    fun providePackageName(@AppContext context: Context): String {
        return context.packageName
    }

    @Provides
    @JvmStatic
    @AppScope
    fun provideFirebaseApp(): FirebaseApp {
        return FirebaseApp.getInstance()
    }

    @Provides
    @JvmStatic
    @AppScope
    fun provideFireStore(fbApp: FirebaseApp): FirebaseFirestore {
        FirebaseFirestore.setLoggingEnabled(true)

        val fireStore = try {
            FirebaseFirestore.getInstance(fbApp)
        } catch (err: NullPointerException){
            // Just try again
            FirebaseFirestore.getInstance(fbApp)
        }
        val settings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build()

        fireStore.firestoreSettings = settings
        return fireStore
    }

    @Provides
    @JvmStatic
    @AppScope
    fun provideFireStorage(fbApp: FirebaseApp): FirebaseStorage {
        return FirebaseStorage.getInstance(fbApp)
    }
}