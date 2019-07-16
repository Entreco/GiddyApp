package nl.entreco.giddyapp.libhorses

import android.net.Uri
import nl.entreco.giddyapp.libcore.HexString
import nl.entreco.giddyapp.libhorses.fetch.FilterOptions

interface HorseService {
    fun retrieve(ids: List<String>, filterOptions: FilterOptions, done: (List<Horse>) -> Unit)
    fun image(ref: String, done: (Uri) -> Unit)
    fun create(
        name: String,
        description: String,
        gender: HorseGender,
        price: HorsePrice,
        category: HorseCategory,
        level: HorseLevel,
        image: Uri,
        startColor: HexString,
        endColor: HexString,
        done: (String) -> Unit
    )

    fun rate(likes: List<String>, dislikes: List<String>, done: (Boolean) -> Unit)
}