package nl.entreco.giddyapp.libauth.user.firebase

import androidx.annotation.Keep
import com.google.firebase.firestore.PropertyName

@Keep
internal data class FbUserHorse (
    @get:PropertyName("n") @set:PropertyName("n") var name: String = ""
)