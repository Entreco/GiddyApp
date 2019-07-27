package nl.entreco.giddyapp.libhorses.data

import nl.entreco.giddyapp.libcore.HexString
import nl.entreco.giddyapp.libhorses.*

internal class HorseMapper {
    fun map(fbHorse: FbHorse, id: String): Horse {
        return Horse.Normal(
            id,
            HexString.from(fbHorse.startColor),
            HexString.from(fbHorse.endColor),
            fbHorse.url,
            fbHorse.posted.toDate().time,
            toDetails(fbHorse)
        )
    }

    private fun toDetails(fbHorse: FbHorse): HorseDetail {
        return HorseDetail(
            fbHorse.name,
            mapDescription(fbHorse),
            mapRatio(fbHorse),
            mapPosted(fbHorse),
            mapGender(fbHorse.gender),
            mapLevel(fbHorse.level),
            mapCategory(fbHorse.category),
            mapPrice(fbHorse.price)
        )
    }

    private fun mapPosted(fbHorse: FbHorse) = HorsePosted(fbHorse.posted.toDate().time)

    private fun mapDescription(fbHorse: FbHorse) =
        if (fbHorse.description.isBlank()) "no description" else fbHorse.description

    private fun mapRatio(fbHorse: FbHorse): HorseRatio {
        val denominator = (fbHorse.likes + fbHorse.dislikes).toFloat()
        return if (denominator <= 0) HorseRatio() else {
            val percentage = "%2.0f".format(fbHorse.likes / denominator * 100L)
            HorseRatio("$percentage%")
        }
    }

    private fun mapGender(gender: Int): HorseGender {
        return if (gender == -1) HorseGender.Unknown
        else HorseGender.values()[gender]
    }

    private fun mapPrice(price: Int): HorsePrice {
        return if (price == -1) HorsePrice.NotForSale
        else HorsePrice.values()[price]
    }

    private fun mapCategory(category: Int): HorseCategory {
        return if (category == -1) HorseCategory.Unknown
        else HorseCategory.values()[category]
    }

    private fun mapLevel(level: Int): HorseLevel {
        return if (level == -1) HorseLevel.Unknown
        else HorseLevel.values()[level]
    }

    fun create(
        name: String,
        description: String,
        gender: HorseGender,
        price: HorsePrice,
        category: HorseCategory,
        level: HorseLevel,
        startColor: HexString,
        endColor: HexString,
        url: String
    ): FbHorse {
        return FbHorse(
            name = name,
            description = description,
            gender = gender.ordinal,
            price = price.ordinal,
            url = url,
            category = category.ordinal,
            level = level.ordinal,
            startColor = startColor.hex(),
            endColor = endColor.hex()
        )
    }
}