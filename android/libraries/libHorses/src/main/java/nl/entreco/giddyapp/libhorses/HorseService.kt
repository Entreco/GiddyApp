package nl.entreco.giddyapp.libhorses

import android.net.Uri
import nl.entreco.giddyapp.libcore.HexString

interface HorseService {
    fun fetch(ids: List<String>, done: (List<Horse>) -> Unit)
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
}