package nl.entreco.giddyapp.libauth.user.firebase

import androidx.annotation.Keep
import com.google.firebase.firestore.PropertyName

@Keep
internal data class FbUser(
    @get:PropertyName("n") @set:PropertyName("n") var name: String = ""
)