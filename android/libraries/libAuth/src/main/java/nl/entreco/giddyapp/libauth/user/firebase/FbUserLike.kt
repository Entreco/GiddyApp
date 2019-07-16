package nl.entreco.giddyapp.libauth.user.firebase

import androidx.annotation.Keep
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.PropertyName

@Keep
internal data class FbUserLike(
    @get:PropertyName("n") @set:PropertyName("n") var name: String = "",
    @get:PropertyName("id") @set:PropertyName("id") var id: String = "",
    @get:PropertyName("r") @set:PropertyName("r") var ref: String = ""
)