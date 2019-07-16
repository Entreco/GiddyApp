package nl.entreco.giddyapp.libauth.user.firebase

import androidx.annotation.Keep
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.PropertyName

@Keep
internal data class FbLike(
    @get:PropertyName("ref") @set:PropertyName("ref") var ref: DocumentReference
)