package nl.entreco.giddyapp.viewer.data

import com.google.firebase.firestore.PropertyName

internal data class FbHorse(
    @PropertyName("name") val name: String = "",
    @PropertyName("startColor") val startColor: String = "#ffffff",
    @PropertyName("endColor") val endColor: String = "#000000"
)