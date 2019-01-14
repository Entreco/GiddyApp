package nl.entreco.giddyapp.viewer

import android.net.Uri

data class Horse(
    val name: String,
    val start: String,
    val end: String,
    val imageRef: String,
    val imageUri: Uri? = null
) {
    companion object {
        fun none(): Horse {
            return Horse("Empty", "#fff000", "#000fff", "none")
        }

        fun error(): Horse {
            return Horse("Error", "#ff0000", "#ff1100", "error")
        }
    }
}