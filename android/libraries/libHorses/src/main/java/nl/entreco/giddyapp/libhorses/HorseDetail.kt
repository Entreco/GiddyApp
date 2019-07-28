package nl.entreco.giddyapp.libhorses

import android.text.format.DateUtils
import androidx.annotation.DrawableRes
import nl.entreco.giddyapp.libhorses.horses.R

data class HorseDetail(
    val name: String,
    val desc: String,
    val ratio: HorseRatio = HorseRatio(),
    val since: HorsePosted = HorsePosted(),
    val gender: HorseGender = HorseGender.Unknown,
    val type: HorseLevel = HorseLevel.Unknown,
    val category: HorseCategory = HorseCategory.Unknown,
    val price: HorsePrice = HorsePrice.NotForSale
)

data class HorseRatio(val ratio: String = "-")

data class HorsePosted(private val stamp: Long = 0) {
    val since: CharSequence
        get() = DateUtils.getRelativeTimeSpanString(
            stamp,
            System.currentTimeMillis(),
            DateUtils.DAY_IN_MILLIS,
            DateUtils.FORMAT_SHOW_YEAR or DateUtils.FORMAT_ABBREV_RELATIVE
        )
}

enum class HorseGender {
    Unknown,
    Stallion,
    Mare,
    Gelding
}

@DrawableRes
fun HorseGender.icon(): Int {
    return when (this) {
        HorseGender.Unknown -> R.drawable.ic_gender_unknown
        HorseGender.Mare -> R.drawable.ic_female
        HorseGender.Stallion -> R.drawable.ic_male
        HorseGender.Gelding -> R.drawable.ic_gelding
    }
}

enum class HorseLevel {
    Unknown,
    Mak,
    B,
    C,
    M,
    Z,
    Zz
}

enum class HorseCategory {
    Unknown,
    Jumping,
    Dressage,
    Eventing,
    Recreation,
    Other
}

enum class HorsePrice {
    NotForSale,
    Range1,
    Range2,
    Range3,
    Range4,
    Range5,
    Range6
}