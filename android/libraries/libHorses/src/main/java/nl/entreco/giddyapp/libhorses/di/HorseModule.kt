package nl.entreco.giddyapp.libhorses.di

import android.content.Context
import com.google.android.gms.common.wrappers.InstantApps
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import nl.entreco.giddyapp.libcore.di.AppContext
import nl.entreco.giddyapp.libcore.di.AppScope
import nl.entreco.giddyapp.libhorses.HorseService
import nl.entreco.giddyapp.libhorses.cycle.HorseCycler
import nl.entreco.giddyapp.libhorses.cycle.InstalledHorseCycler
import nl.entreco.giddyapp.libhorses.cycle.InstantHorseCycler
import nl.entreco.giddyapp.libhorses.data.FbHorseService

@Module
object HorseModule {

    @Provides
    @JvmStatic
    internal fun provideFireStore(): FirebaseFirestore {
        val fireStore = FirebaseFirestore.getInstance()
        val settings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build()
        fireStore.firestoreSettings = settings
        return fireStore
    }

    @Provides
    @JvmStatic
    internal fun provideFireStorage(): FirebaseStorage {
        return FirebaseStorage.getInstance()
    }

    @Provides
    @JvmStatic
    @AppScope
    internal fun provideFbHorseService(db: FirebaseFirestore, storage: FirebaseStorage): HorseService =
        FbHorseService(db, storage)

    @Provides
    @JvmStatic
    internal fun provideRecycler(
        @AppContext context: Context
    ): HorseCycler {
        return if (InstantApps.isInstantApp(context)) {
            InstantHorseCycler()
        } else {
            InstalledHorseCycler()
        }
    }
}