package nl.entreco.giddyapp.viewer.domain

import kotlin.random.Random

data class HorseDetail(
    val name: String,
    val desc: String,
    val gender: HorseGender = HorseGender.Unknown,
    val type: String = randomLevel(),
    val category: String = randomCategory(),
    val price: String = randomPrice()
)

sealed class HorseGender(val number: Number) {
    object Unknown : HorseGender(-1)
    object Male : HorseGender(0)
    object Female : HorseGender(1)
    object Gelding : HorseGender(2)
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
