package nl.entreco.giddyapp.libmatches.di

import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import nl.entreco.giddyapp.libcore.di.AppScope
import nl.entreco.giddyapp.libmatches.MatchService
import nl.entreco.giddyapp.libmatches.data.FbMatchService

@Module
object MatchModule {

    @Provides
    @JvmStatic
    @AppScope
    internal fun provideFbMatchService(db: FirebaseFirestore): MatchService {
        return FbMatchService(db)
    }
}