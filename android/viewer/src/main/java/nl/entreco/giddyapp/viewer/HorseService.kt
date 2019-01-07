package nl.entreco.giddyapp.viewer

import android.net.Uri

interface HorseService {
    fun fetch(id: String, done: (Horse?) -> Unit)
    fun image(ref: String, done: (Uri) -> Unit)
}