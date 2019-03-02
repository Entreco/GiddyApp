package nl.entreco.giddyapp.libcore

import android.graphics.Color
import androidx.annotation.ColorInt

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