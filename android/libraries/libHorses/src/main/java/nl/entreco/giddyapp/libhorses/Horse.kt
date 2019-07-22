package nl.entreco.giddyapp.libhorses

import android.net.Uri
import nl.entreco.giddyapp.libcore.HexString

sealed class Horse {

    val id: String
        get() = when (this) {
            is Normal -> _id
            is NotFound -> _id
            else -> ""
        }

    val name: String
        get() = when (this) {
            is Normal -> details.name
            is Loading -> "Loading"
            is Error -> "Oops"
            else -> ""
        }

    val imageUri: Uri?
        get() = when (this) {
            is Normal -> this.uri
            else -> null
        }

    val imageRef: String
        get() = when (this) {
            is Normal -> ref
            else -> "none"
        }

    fun update(imageUri: Uri?): Horse = when (this) {
        is Normal -> this.copy(uri = imageUri)
        else -> this
    }

    data class Normal(
        val _id: String,
        val start: HexString,
        val end: HexString,
        val ref: String,
        val posted: Long,
        val details: HorseDetail,
        val uri: Uri? = null
    ) : Horse()


    data class NotFound(val _id: String) : Horse()

    object Loading : Horse()

    object None : Horse()

    data class Error(val msg: String) : Horse()
}

