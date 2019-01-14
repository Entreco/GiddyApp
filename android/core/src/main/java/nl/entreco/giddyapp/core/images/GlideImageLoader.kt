package nl.entreco.giddyapp.core.images

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.net.URL

class GlideImageLoader : ImageLoader {
    override fun get(uri: Uri): Bitmap {
        val url = URL(uri.toString())
        return BitmapFactory.decodeStream(url.openStream())
    }
}