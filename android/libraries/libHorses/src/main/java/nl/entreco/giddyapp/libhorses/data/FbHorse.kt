package nl.entreco.giddyapp.libhorses.data

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
    @get:PropertyName("gen") @set:PropertyName("gen") var gender: Int = -1,
    @get:PropertyName("pr") @set:PropertyName("pr") var price: Int = -1,
    @get:PropertyName("cat") @set:PropertyName("cat") var category: Int = -1,
    @get:PropertyName("lvl") @set:PropertyName("lvl") var level: Int = -1
)