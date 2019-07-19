package nl.entreco.giddyapp.libpicker

import android.content.Context
import android.net.Uri

interface ImageLabeller {
    fun getLabels(
        context: Context, uri: Uri,
        done: (List<String>) -> Unit
    )
}