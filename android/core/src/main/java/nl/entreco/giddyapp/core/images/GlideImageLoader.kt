package nl.entreco.giddyapp.core.images

import android.net.Uri
import android.widget.ImageView

class GlideImageLoader : ImageLoader {
    override fun get(view: ImageView, uri: Uri?) {
        GlideApp.with(view).load(uri).into(view)
    }
}