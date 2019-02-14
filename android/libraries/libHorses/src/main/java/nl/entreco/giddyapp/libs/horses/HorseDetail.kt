package nl.entreco.giddyapp.libs.horses

import android.content.Context
import androidx.annotation.DrawableRes
import kotlin.random.Random

data class HorseDetail(
    val name: String,
    val desc: String,
    val gender: HorseGender = HorseGender.Unknown,
    val type: HorseLevel = HorseLevel.Unknown,
    val category: HorseCategory = HorseCategory.Unknown,
    val price: HorsePrice = HorsePrice.Unknown
)

sealed class HorseGender(val number: Int) {
    object Unknown : HorseGender(0)
    object Male : HorseGender(1)
    object Female : HorseGender(2)
    object Gelding : HorseGender(3)
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

sealed class HorseLevel(val number: Int) {
    object Unknown : HorseLevel(-1)
    object Mak : HorseLevel(0)
    object B : HorseLevel(1)
    object C : HorseLevel(2)
    object M : HorseLevel(3)
    object Z : HorseLevel(4)
    object Zz : HorseLevel(5)
}

sealed class HorseCategory(val number: Int) {
    object Unknown : HorseCategory(-1)
    object Jumping : HorseCategory(0)
    object Dressage : HorseCategory(1)
    object Eventing : HorseCategory(2)
    object Recreation : HorseCategory(3)
    object Other : HorseCategory(4)
}

sealed class HorsePrice(val number: Int) {
    object Unknown : HorsePrice(-1)
    object Male : HorsePrice(0)
    object Female : HorsePrice(1)
    object Gelding : HorsePrice(2)
}

private val categories = listOf("Springen", "Dressuur", "Eventing", "Recreatie", "Overige")

private val levels = listOf("Zadelmak", "B", "C", "M", "Z", "ZZ")
private fun randomLevel(): String {
    return levels[Random.nextInt(until = levels.size)]
}

private fun randomPrice(): String {
    val random = Random.nextInt(11)
    return "${random}K - ${random + 1}K"
}
