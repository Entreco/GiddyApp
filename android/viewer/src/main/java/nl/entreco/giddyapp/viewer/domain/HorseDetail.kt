package nl.entreco.giddyapp.viewer.domain

import kotlin.random.Random

data class HorseDetail(
    val name: String,
    val desc: String,
    val likes: Int = randomLikes(),
    val cat: String = randomLikes().toString(),
    val price: String = randomPrice()
)

private fun randomLikes(): Int {
    return Random.nextInt(1000)
}

private fun randomPrice(): String {
    val random = Random.nextInt(11)
    val from = "%.0f".format(random * 1000F)
    val to = "%.0f".format((random + 1) * 1000F)
    return "$from - $to"
}
