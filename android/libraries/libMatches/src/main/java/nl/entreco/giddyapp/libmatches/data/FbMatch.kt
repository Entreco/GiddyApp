package nl.entreco.giddyapp.libmatches.data

import androidx.annotation.Keep
import com.google.firebase.firestore.PropertyName

@Keep
internal data class FbMatch(
    @get:PropertyName("u") @set:PropertyName("u") var userId: String = "",
    @get:PropertyName("h") @set:PropertyName("h") var horseId: String = "",
    @get:PropertyName("n") @set:PropertyName("n") var name: String = "",
    @get:PropertyName("r") @set:PropertyName("r") var ref: String = ""
)