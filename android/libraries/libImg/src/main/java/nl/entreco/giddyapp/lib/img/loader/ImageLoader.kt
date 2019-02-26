package nl.entreco.giddyapp.lib.img.loader

import android.net.Uri
import android.widget.ImageView

interface ImageLoader {
    fun get(view: ImageView, uri: Uri?)
}