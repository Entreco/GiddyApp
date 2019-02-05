package nl.entreco.giddyapp.libs.horses

import android.net.Uri
import nl.entreco.giddyapp.core.HexString

data class Horse(
    val start: nl.entreco.giddyapp.core.HexString,
    val end: nl.entreco.giddyapp.core.HexString,
    val imageRef: String,
    val imageExt: String,
    val posted: Long,
    val details: HorseDetail,
    val imageUri: Uri? = null
) {
    companion object {
        fun none(): Horse {
            return Horse(HexString.Red, HexString.Blue, "none", "-", 0L, HorseDetail("Empty", ""))
        }

        fun notFound(id: String): Horse {
            return Horse(HexString.Red, HexString.Blue, "notFound", "-", 0L, HorseDetail("Horse with $id found", ""))
        }

        fun error(): Horse {
            return Horse(HexString.Red, HexString.Blue, "error", "-", 0L, HorseDetail("Error", "--"))
        }
    }
}

