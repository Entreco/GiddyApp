package nl.entreco.giddyapp.libs.horses

import android.graphics.Color
import android.net.Uri
import androidx.annotation.ColorInt

data class Horse(
    val start: HexString,
    val end: HexString,
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

sealed class HexString(private val colorString: String) {

    @ColorInt
    fun color(): Int {
        return Color.parseColor(colorString)
    }

    fun hex(): String {
        return colorString
    }

    companion object {
        fun from(hexString: String): HexString {
            return tryOrDefault {
                Color.parseColor(hexString)
                Hex(hexString)
            }
        }

        fun from(rgbInt: Int?): HexString {
            return if (rgbInt == null) White
            else tryOrDefault {
                val hexString = "#${Integer.toHexString(rgbInt)}"
                Hex(hexString)
            }
        }

        private fun tryOrDefault(f: () -> Hex): HexString {
            return try {
                f()
            } catch (nope: IllegalArgumentException) {
                White
            }
        }
    }

    data class Hex(val hex: String) : HexString(hex)
    object White : HexString("#ffffff")
    object Red : HexString("#ff000000")
    object Blue : HexString("#0000ff")
}
