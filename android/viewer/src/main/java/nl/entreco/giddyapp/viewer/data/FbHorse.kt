package nl.entreco.giddyapp.viewer.data

import com.google.firebase.firestore.PropertyName
import java.util.*

internal data class FbHorse(
    @PropertyName("name") val name: String = "",
    @PropertyName("startColor") val startColor: String = "#ffffff",
    @PropertyName("endColor") val endColor: String = "#000000",
    @PropertyName("posted") val posted: Date = Date(0L),
    @PropertyName("ext") val ext: String = "jpg"
)