package nl.entreco.giddyapp.libs.horses

import androidx.annotation.DrawableRes

data class HorseDetail(
    val name: String,
    val desc: String,
    val gender: HorseGender = HorseGender.Unknown,
    val type: HorseLevel = HorseLevel.Unknown,
    val category: HorseCategory = HorseCategory.Unknown,
    val price: HorsePrice = HorsePrice.NotForSale
)

enum class HorseGender {
    Unknown,
    Male,
    Female,
    Gelding
}

@DrawableRes
fun HorseGender.icon(): Int {
    return when (this) {
        HorseGender.Unknown -> R.drawable.ic_gender_unknown
        HorseGender.Female -> R.drawable.ic_female
        HorseGender.Male -> R.drawable.ic_male
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
        HorseCategory.Unknown -> R.drawable.ic_filter
        HorseCategory.Jumping -> R.drawable.ic_filter
        HorseCategory.Dressage -> R.drawable.ic_filter
        HorseCategory.Eventing -> R.drawable.ic_filter
        HorseCategory.Recreation -> R.drawable.ic_filter
        HorseCategory.Other -> R.drawable.ic_filter
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