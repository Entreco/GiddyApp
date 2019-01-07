package nl.entreco.giddyapp.core

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Subcomponent

@ApiScope
@Subcomponent(modules = [ApiModule::class])
interface ApiComponent{
    fun api() : Api
    fun firestore() : FirebaseFirestore
    fun firestorage() : FirebaseStorage
}