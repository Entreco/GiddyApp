package nl.entreco.giddyapp.viewer.domain

import android.net.Uri

interface HorseService {
    fun fetch(ids: List<String?>, done: (List<Horse>) -> Unit)
    fun image(ref: String, done: (Uri) -> Unit)
}