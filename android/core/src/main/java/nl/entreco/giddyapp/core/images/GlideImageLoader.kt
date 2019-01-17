package nl.entreco.giddyapp.core.images

import android.net.Uri
import android.util.Log
import android.widget.ImageView

class GlideImageLoader : ImageLoader {
    override fun get(view: ImageView, uri: Uri?) {
        Log.i("WOAH", "image: ${view.drawable}")
        Log.i("WOAH", "uri: $uri")
        Log.i("WOAH", "scaleX: ${view.scaleX} scaleY: ${view.scaleY}")
        Log.i("WOAH", "width: ${view.width} height: ${view.height}")
        Log.i("WOAH", "mw: ${view.measuredWidth} mh: ${view.measuredHeight}")
        GlideApp.with(view).load(uri).placeholder(view.background).into(view)
    }
}