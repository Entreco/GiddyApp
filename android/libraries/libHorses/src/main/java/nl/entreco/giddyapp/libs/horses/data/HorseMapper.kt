package nl.entreco.giddyapp.libs.horses.data

import nl.entreco.giddyapp.core.HexString
import nl.entreco.giddyapp.libs.horses.*

internal class HorseMapper {
    fun map(fbHorse: FbHorse, imageRef: String): Horse {
        return Horse(
            HexString.from(fbHorse.startColor),
            HexString.from(fbHorse.endColor),
            imageRef,
            fbHorse.ext,
            fbHorse.posted.time,
            toDetails(fbHorse)
        )
    }

    private fun toDetails(fbHorse: FbHorse): HorseDetail {
        return HorseDetail(
            fbHorse.name,
            mapDescription(fbHorse),
            mapGender(fbHorse.gender),
            mapLevel(fbHorse.level),
            mapCategory(fbHorse.category),
            mapPrice(fbHorse.price)
        )
    }

    private fun mapDescription(fbHorse: FbHorse) =
        if (fbHorse.description.isBlank()) "no description" else fbHorse.description

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
        name: String, description: String, gender: HorseGender,
        price: HorsePrice,
        category: HorseCategory,
        level: HorseLevel,
        startColor: HexString,
        endColor: HexString
    ): FbHorse {
        return FbHorse(
            name = name,
            description = description,
            gender = gender.ordinal,
            price = price.ordinal,
            category = category.ordinal,
            level = level.ordinal,
            startColor = startColor.hex(),
            endColor = endColor.hex()
        )
    }
}