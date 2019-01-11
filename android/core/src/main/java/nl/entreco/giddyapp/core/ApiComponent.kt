package nl.entreco.giddyapp.core

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Subcomponent

@ApiScope
@Subcomponent(modules = [ApiModule::class])
interface ApiComponent{
    fun context(): Context
    fun api() : Api
    fun cache() : ImageCache
    fun firestore() : FirebaseFirestore
    fun firestorage() : FirebaseStorage
}