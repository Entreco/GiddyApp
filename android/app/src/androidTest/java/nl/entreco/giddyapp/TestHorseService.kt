package nl.entreco.giddyapp

import android.net.Uri
import nl.entreco.giddyapp.libcore.HexString
import nl.entreco.giddyapp.libhorses.*

class TestHorseService : HorseService {
    override fun fetch(ids: List<String>, done: (List<Horse>) -> Unit) {
        done(emptyList())
    }

    override fun image(ref: String, done: (Uri) -> Unit) {
        done(Uri.parse("test"))
    }

    override fun create(
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
    ) {
        done("testId")
    }
}