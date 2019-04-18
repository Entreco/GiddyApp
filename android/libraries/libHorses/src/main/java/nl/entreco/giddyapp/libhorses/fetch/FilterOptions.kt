package nl.entreco.giddyapp.libhorses.fetch

import nl.entreco.giddyapp.libhorses.*

data class FilterOptions(
    private var count: Int = 0,
    private var filterStallion: Float = 0F,
    private var countStallion: Int = 0,
    private var filterMare: Float = 0F,
    private var countMare: Int = 0,
    private var filterGelding: Float = 0F,
    private var countGelding: Int = 0,
    private var filterMak: Float = 0F,
    private var countMak: Int = 0,
    private var filterB: Float = 0F,
    private var countB: Int = 0,
    private var filterC: Float = 0F,
    private var countC: Int = 0,
    private var filterM: Float = 0F,
    private var countM: Int = 0,
    private var filterZ: Float = 0F,
    private var countZ: Int = 0,
    private var filterZz: Float = 0F,
    private var countZz: Int = 0,
    private var filterJumping: Float = 0F,
    private var countJumping: Int = 0,
    private var filterDressage: Float = 0F,
    private var countDressage: Int = 0,
    private var filterEventing: Float = 0F,
    private var countEventing: Int = 0,
    private var filterRecreation: Float = 0F,
    private var countRecreation: Int = 0,
    private var filterOther: Float = 0F,
    private var countOther: Int = 0,
    private var filterNotForSale: Float = 0F,
    private var countNotForSale: Int = 0,
    private var filterRange1: Float = 0F,
    private var countRange1: Int = 0,
    private var filterRange2: Float = 0F,
    private var countRange2: Int = 0,
    private var filterRange3: Float = 0F,
    private var countRange3: Int = 0,
    private var filterRange4: Float = 0F,
    private var countRange4: Int = 0,
    private var filterRange5: Float = 0F,
    private var countRange5: Int = 0,
    private var filterRange6: Float = 0F,
    private var countRange6: Int = 0
) {

    fun improve(detail: HorseDetail, like: Boolean) {
        count++
        improveGender(detail.gender, if (like) 1F else -1F)
        improveLevel(detail.type, if (like) 1F else -1F)
        improveCategory(detail.category, if (like) 1F else -1F)
        improvePrice(detail.price, if (like) 1F else -1F)
    }

    private fun improveGender(gender: HorseGender, value: Float) {
        when (gender) {
            HorseGender.Stallion -> filterStallion += value.also { countStallion++ }
            HorseGender.Mare -> filterMare += value.also { countMare++ }
            HorseGender.Gelding -> filterGelding += value.also { countGelding++ }
            else -> {
            }
        }
    }

    private fun improveLevel(level: HorseLevel, value: Float) {
        when (level) {
            HorseLevel.Mak -> filterMak += value.also { countMak++ }
            HorseLevel.B -> filterB += value.also { countB++ }
            HorseLevel.C -> filterC += value.also { countC++ }
            HorseLevel.M -> filterM += value.also { countM++ }
            HorseLevel.Z -> filterZ += value.also { countZ++ }
            HorseLevel.Zz -> filterZz += value.also { countZz++ }
            else -> {
            }
        }
    }

    private fun improveCategory(category: HorseCategory, value: Float) {
        when (category) {
            HorseCategory.Jumping -> filterJumping = value.also { countJumping++ }
            HorseCategory.Dressage -> filterDressage = value.also { countDressage++ }
            HorseCategory.Eventing -> filterEventing = value.also { countEventing++ }
            HorseCategory.Recreation -> filterRecreation = value.also { countRecreation++ }
            HorseCategory.Other -> filterOther = value.also { countOther++ }
            else -> {
            }
        }
    }

    private fun improvePrice(price: HorsePrice, value: Float) {
        when (price) {
            HorsePrice.NotForSale -> filterNotForSale = value.also { countNotForSale++ }
            HorsePrice.Range1 -> filterRange1 = value.also { countRange1++ }
            HorsePrice.Range2 -> filterRange2 = value.also { countRange2++ }
            HorsePrice.Range3 -> filterRange3 = value.also { countRange3++ }
            HorsePrice.Range4 -> filterRange4 = value.also { countRange4++ }
            HorsePrice.Range5 -> filterRange5 = value.also { countRange5++ }
            HorsePrice.Range6 -> filterRange6 = value.also { countRange6++ }
        }
    }

    private val includeThreshold = 0.5F
    private val excludeThreshold = -0.5F

    fun includes(): List<Pair<String, Int>> {
        if (count == 0) return emptyList()

        val includes = mutableListOf<Pair<String, Int>>()
        when {
            // Gender
            filterStallion / countStallion > includeThreshold -> includes += "gen" to HorseGender.Stallion.ordinal
            filterMare / countMare > includeThreshold -> includes += "gen" to HorseGender.Mare.ordinal
            filterGelding / countGelding > includeThreshold -> includes += "gen" to HorseGender.Gelding.ordinal
            // Level
            filterMak / countMak > includeThreshold -> includes += "lvl" to HorseLevel.Mak.ordinal
            filterB / countB > includeThreshold -> includes += "lvl" to HorseLevel.B.ordinal
            filterC / countC > includeThreshold -> includes += "lvl" to HorseLevel.C.ordinal
            filterM / countM > includeThreshold -> includes += "lvl" to HorseLevel.M.ordinal
            filterZ / countZ > includeThreshold -> includes += "lvl" to HorseLevel.Z.ordinal
            filterZz / countZz > includeThreshold -> includes += "lvl" to HorseLevel.Zz.ordinal
            // Category
            filterJumping / countJumping > includeThreshold -> includes += "cat" to HorseCategory.Jumping.ordinal
            filterDressage / countDressage > includeThreshold -> includes += "cat" to HorseCategory.Dressage.ordinal
            filterEventing / countEventing > includeThreshold -> includes += "cat" to HorseCategory.Eventing.ordinal
            filterRecreation / countRecreation > includeThreshold -> includes += "cat" to HorseCategory.Recreation.ordinal
            filterOther / countOther > includeThreshold -> includes += "cat" to HorseCategory.Other.ordinal
            // Price
            filterNotForSale / countNotForSale > includeThreshold -> includes += "pr" to HorsePrice.NotForSale.ordinal
            filterRange1 / countRange1 > includeThreshold -> includes += "pr" to HorsePrice.Range1.ordinal
            filterRange2 / countRange2 > includeThreshold -> includes += "pr" to HorsePrice.Range2.ordinal
            filterRange3 / countRange3 > includeThreshold -> includes += "pr" to HorsePrice.Range3.ordinal
            filterRange4 / countRange4 > includeThreshold -> includes += "pr" to HorsePrice.Range4.ordinal
            filterRange5 / countRange5 > includeThreshold -> includes += "pr" to HorsePrice.Range5.ordinal
            filterRange6 / countRange6 > includeThreshold -> includes += "pr" to HorsePrice.Range6.ordinal
        }

        return includes
    }

    fun excludes(): List<Pair<String, Int>> {
        if (count == 0) return emptyList()

        val excludes = mutableListOf<Pair<String, Int>>()
        when {
            // Gender
            filterStallion / countStallion < excludeThreshold -> excludes += "gen" to HorseGender.Stallion.ordinal
            filterMare / countMare < excludeThreshold -> excludes += "gen" to HorseGender.Mare.ordinal
            filterGelding / countGelding < excludeThreshold -> excludes += "gen" to HorseGender.Gelding.ordinal
            // Level
            filterMak / countMak < excludeThreshold -> excludes += "lvl" to HorseLevel.Mak.ordinal
            filterB / countB < excludeThreshold -> excludes += "lvl" to HorseLevel.B.ordinal
            filterC / countC < excludeThreshold -> excludes += "lvl" to HorseLevel.C.ordinal
            filterM / countM < excludeThreshold -> excludes += "lvl" to HorseLevel.M.ordinal
            filterZ / countZ < excludeThreshold -> excludes += "lvl" to HorseLevel.Z.ordinal
            filterZz / countZz < excludeThreshold -> excludes += "lvl" to HorseLevel.Zz.ordinal
            // Category
            filterJumping / countJumping < excludeThreshold -> excludes += "cat" to HorseCategory.Jumping.ordinal
            filterDressage / countDressage < excludeThreshold -> excludes += "cat" to HorseCategory.Dressage.ordinal
            filterEventing / countEventing < excludeThreshold -> excludes += "cat" to HorseCategory.Eventing.ordinal
            filterRecreation / countRecreation < excludeThreshold -> excludes += "cat" to HorseCategory.Recreation.ordinal
            filterOther / countOther < excludeThreshold -> excludes += "cat" to HorseCategory.Other.ordinal
            // Price
            filterNotForSale / countNotForSale < excludeThreshold-> excludes += "pr" to HorsePrice.NotForSale.ordinal
            filterRange1 / countRange1 < excludeThreshold-> excludes += "pr" to HorsePrice.Range1.ordinal
            filterRange2 / countRange2 < excludeThreshold-> excludes += "pr" to HorsePrice.Range2.ordinal
            filterRange3 / countRange3 < excludeThreshold-> excludes += "pr" to HorsePrice.Range3.ordinal
            filterRange4 / countRange4 < excludeThreshold-> excludes += "pr" to HorsePrice.Range4.ordinal
            filterRange5 / countRange5 < excludeThreshold-> excludes += "pr" to HorsePrice.Range5.ordinal
            filterRange6 / countRange6 < excludeThreshold-> excludes += "pr" to HorsePrice.Range6.ordinal
        }

        return excludes
    }
}