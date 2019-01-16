package nl.entreco.giddyapp.viewer

import android.net.Uri
import kotlin.random.Random

data class Horse(
    val name: String,
    val start: String,
    val end: String,
    val imageRef: String,
    val imageUri: Uri? = null,
    val price: String = randomPrice()
) {
    companion object {
        fun none(): Horse {
            return Horse("Empty", "#fff000", "#000fff", "none")
        }

        fun error(): Horse {
            return Horse("Error", "#ff0000", "#ff1100","error")
        }
    }

}

private fun randomPrice() : String {
    val random = Random.nextInt(11)
    val from = "%.0f".format(random * 1000F)
    val to = "%.0f".format((random + 1) * 1000F)
    return "$from - $to EUR ?"
}
