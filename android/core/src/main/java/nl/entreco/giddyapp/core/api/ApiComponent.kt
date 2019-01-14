package nl.entreco.giddyapp.core.api

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Subcomponent

@Subcomponent(modules = [ApiModule::class])
interface ApiComponent {
    fun context(): Context
    fun api(): Api
    fun firestore(): FirebaseFirestore
    fun firestorage(): FirebaseStorage
}