package nl.entreco.giddyapp.libs.horses

import android.net.Uri

interface HorseService {
    fun fetch(ids: List<String>, done: (List<Horse>) -> Unit)
    fun image(ref: String, done: (Uri) -> Unit)
    fun create(name: String,
               description: String,
               gender: HorseGender,
               image: Uri,
               startColor: HexString,
               endColor: HexString,
               done: (String) -> Unit)
}