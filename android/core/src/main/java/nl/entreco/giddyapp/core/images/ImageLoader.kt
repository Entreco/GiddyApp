package nl.entreco.giddyapp.core.images

import android.graphics.Bitmap
import android.net.Uri

interface ImageLoader {
    fun get(uri: Uri) : Bitmap
}