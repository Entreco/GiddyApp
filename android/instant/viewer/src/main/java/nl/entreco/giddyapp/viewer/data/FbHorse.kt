package nl.entreco.giddyapp.viewer.data

import androidx.annotation.Keep
import com.google.firebase.firestore.PropertyName
import java.util.*

@Keep
internal data class FbHorse(
    @get:PropertyName("n") @set:PropertyName("n") var name: String = "",
    @get:PropertyName("d") @set:PropertyName("d") var description: String = "",
    @get:PropertyName("s") @set:PropertyName("s") var startColor: String = "#ffffff",
    @get:PropertyName("e") @set:PropertyName("e") var endColor: String = "#000000",
    @get:PropertyName("p") @set:PropertyName("p") var posted: Date = Date(0L),
    @get:PropertyName("ext") @set:PropertyName("ext") var ext: String = "jpg",
    @get:PropertyName("g") @set:PropertyName("g") var gender: Int = -1
)