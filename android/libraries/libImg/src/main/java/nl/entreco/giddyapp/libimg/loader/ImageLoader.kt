package nl.entreco.giddyapp.libimg.loader

import android.net.Uri
import android.widget.ImageView

interface ImageLoader {
    fun get(view: ImageView, uri: Uri?)
}