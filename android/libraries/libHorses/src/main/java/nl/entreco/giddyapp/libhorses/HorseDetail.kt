package nl.entreco.giddyapp.libhorses

import android.text.format.DateUtils
import androidx.annotation.DrawableRes
import nl.entreco.giddyapp.libhorses.horses.R

data class HorseDetail(
    val name: String,
    val desc: String,
    val ratio: HorseRatio = HorseRatio(),
    val location: HorseLocation = HorseLocation.Unknown,
    val since: HorsePosted = HorsePosted(),
    val gender: HorseGender = HorseGender.Unknown,
    val type: HorseLevel = HorseLevel.Unknown,
    val category: HorseCategory = HorseCategory.Unknown,
    val price: HorsePrice = HorsePrice.NotForSale
)

data class HorseRatio(val ratio: String = "-")

sealed class HorseLocation {

    abstract val name: String

    data class Area(val description: String, val country: String, val lat: Double, val lon: Double) : HorseLocation(){
        override val name = description
    }

    object Unknown : HorseLocation(){
        override val name = "-"
    }

}

data class HorsePosted(private val stamp: Long = 0) {
    val since: CharSequence
        get() = DateUtils.getRelativeTimeSpanString(
            stamp,
            System.currentTimeMillis(),
            DateUtils.MINUTE_IN_MILLIS,
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

@DrawableRes
fun HorseCategory.icon(): Int {
    return when (this) {
        HorseCategory.Dressage -> R.drawable.ic_category_dressage
        HorseCategory.Eventing -> R.drawable.ic_category_dressage
        HorseCategory.Jumping -> R.drawable.ic_category_jumping
        HorseCategory.Recreation -> R.drawable.ic_category_recreation
        HorseCategory.Unknown -> R.drawable.ic_category_dressage
        HorseCategory.Other -> R.drawable.ic_category_other
    }
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