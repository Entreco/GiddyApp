package nl.entreco.giddyapp.libhorses

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import nl.entreco.giddyapp.libcore.HexString
import nl.entreco.giddyapp.libhorses.horses.R

sealed class Horse() {

    val id: String
        get() = when (this) {
            is Normal -> _id
            is NotFound -> _id
            else -> ""
        }

    val name: String
        get() = when (this) {
            is Normal -> details.name
            else -> ""
        }

    val desc: String
        get() = when (this) {
            is Normal -> details.desc
            else -> ""
        }

    val gender: HorseGender
        get() = when (this) {
            is Normal -> details.gender
            else -> HorseGender.Unknown
        }

    val type: HorseLevel
        get() = when (this) {
            is Normal -> details.type
            else -> HorseLevel.Unknown
        }

    val price: HorsePrice
        get() = when (this) {
            is Normal -> details.price
            else -> HorsePrice.NotForSale
        }

    val category: HorseCategory
        get() = when (this) {
            is Normal -> details.category
            else -> HorseCategory.Unknown
        }

    val imageUri: Uri?
        get() = when (this) {
            is Normal -> uri
            is NotFound -> Uri.parse("android.resource://nl.entreco.giddyapp/${R.drawable.empty}")
            is Loading -> Uri.parse("android.resource://nl.entreco.giddyapp/${R.drawable.empty}")
            else -> null
        }

    val imageRef: String
        get() = when (this) {
            is Normal -> ref
            is NotFound -> "notFound"
            is Loading -> "loading"
            else -> "else"
        }

    val gradient: Drawable
        get() = when (this) {
            is Normal -> GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                intArrayOf(start.color(), end.color())
            )
            else -> GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                intArrayOf(Color.TRANSPARENT, Color.BLACK)
            )
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

    object Error : Horse()
}

