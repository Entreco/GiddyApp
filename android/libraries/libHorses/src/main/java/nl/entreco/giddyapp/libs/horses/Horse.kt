package nl.entreco.giddyapp.libs.horses

import android.net.Uri

data class Horse(
    val start: String,
    val end: String,
    val imageRef: String,
    val imageExt: String,
    val posted: Long,
    val details: HorseDetail,
    val imageUri: Uri? = null
) {
    companion object {
        fun none(): Horse {
            return Horse("#fff000", "#000fff", "none", "-", 0L, HorseDetail("Empty", ""))
        }

        fun notFound(id: String): Horse {
            return Horse("#fff000", "#000fff", "notFound", "-", 0L, HorseDetail("Horse with $id found", ""))
        }

        fun error(): Horse {
            return Horse("#ff0000", "#ff1100", "error", "-", 0L, HorseDetail("Error", "--"))
        }
    }

}
