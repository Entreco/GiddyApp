package nl.entreco.giddyapp.libmatches.data

import androidx.annotation.Keep
import com.google.firebase.Timestamp
import com.google.firebase.firestore.PropertyName

@Keep
internal data class FbLike(
    @get:PropertyName("u") @set:PropertyName("u") var user: String = "",
    @get:PropertyName("h") @set:PropertyName("h") var horse: String = "",
    @get:PropertyName("t") @set:PropertyName("t") var stamp: Timestamp = Timestamp.now()
)