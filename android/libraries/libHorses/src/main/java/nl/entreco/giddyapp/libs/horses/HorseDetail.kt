package nl.entreco.giddyapp.libs.horses

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
    object Unknown : HorseGender(-1)
    object Male : HorseGender(0)
    object Female : HorseGender(1)
    object Gelding : HorseGender(2)
}

sealed class HorseLevel(val number: Int) {
    object Unknown : HorseLevel(-1)
    object Male : HorseLevel(0)
    object Female : HorseLevel(1)
    object Gelding : HorseLevel(2)
}

sealed class HorseCategory(val number: Int) {
    object Unknown : HorseCategory(-1)
    object Male : HorseCategory(0)
    object Female : HorseCategory(1)
    object Gelding : HorseCategory(2)
}

sealed class HorsePrice(val number: Int) {
    object Unknown : HorsePrice(-1)
    object Male : HorsePrice(0)
    object Female : HorsePrice(1)
    object Gelding : HorsePrice(2)
}

private val categories = listOf("Springen", "Dressuur", "Eventing", "Recreatie", "Overige")
private fun randomCategory(): String {
    return categories[Random.nextInt(until = categories.size)]
}

private val levels = listOf("Zadelmak", "B", "C", "M", "Z", "ZZ")
private fun randomLevel(): String {
    return levels[Random.nextInt(until = levels.size)]
}

private fun randomPrice(): String {
    val random = Random.nextInt(11)
    return "${random}K - ${random + 1}K"
}
