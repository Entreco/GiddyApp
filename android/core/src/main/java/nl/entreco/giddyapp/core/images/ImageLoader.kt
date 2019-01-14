package nl.entreco.giddyapp.core.images

import android.net.Uri
import android.widget.ImageView

interface ImageLoader {
    fun get(view: ImageView, uri: Uri?)
}