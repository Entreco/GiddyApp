package nl.entreco.giddyapp.libauth.user.firebase

import androidx.annotation.Keep
import com.google.firebase.firestore.PropertyName

@Keep
internal data class FbUser(
    @get:PropertyName("n") @set:PropertyName("n") var name: String = "",
    @get:PropertyName("l") @set:PropertyName("l") var likes: List<String> = emptyList(),
    @get:PropertyName("d") @set:PropertyName("d") var dislikes: List<String> = emptyList()
)