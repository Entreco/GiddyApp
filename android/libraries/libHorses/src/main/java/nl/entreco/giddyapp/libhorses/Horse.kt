package nl.entreco.giddyapp.libhorses

import android.net.Uri
import nl.entreco.giddyapp.libcore.HexString

data class Horse(
    val id: String,
    val start: HexString,
    val end: HexString,
    val imageRef: String,
    val posted: Long,
    val details: HorseDetail,
    val imageUri: Uri? = null
) {
    companion object {
        fun none(): Horse {
            return Horse(
                "none",
                HexString.Transparent,
                HexString.Transparent,
                "none",
                0L,
                HorseDetail("Loading", "even geduld aub...")
            )
        }

        fun notFound(id: String): Horse {
            return Horse(
                id,
                HexString.Red,
                HexString.Blue,
                "notFound",
                0L,
                HorseDetail("Horse with $id found", "")
            )
        }

        fun error(): Horse {
            return Horse(
                "error",
                HexString.Red,
                HexString.Blue,
                "error",
                0L,
                HorseDetail("Error", "--")
            )
        }
    }
}

