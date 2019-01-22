package nl.entreco.giddyapp.viewer.data

import androidx.annotation.Keep
import com.google.firebase.firestore.PropertyName
import java.util.*

@Keep
internal data class FbHorse(
    @PropertyName("name") val name: String = "",
    @PropertyName("description") val description: String = "",
    @PropertyName("startColor") val startColor: String = "#ffffff",
    @PropertyName("endColor") val endColor: String = "#000000",
    @PropertyName("posted") val posted: Date = Date(0L),
    @PropertyName("ext") val ext: String = "jpg",
    @PropertyName("g") val g: Int = -1
)