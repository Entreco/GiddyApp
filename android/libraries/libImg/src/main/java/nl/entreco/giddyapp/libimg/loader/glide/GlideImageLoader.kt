package nl.entreco.giddyapp.libimg.loader.glide

import android.net.Uri
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import nl.entreco.giddyapp.libimg.loader.ImageLoader


internal class GlideImageLoader(private val screenWidth: Int, private val screenHeight: Int) :
    ImageLoader {
    override fun get(view: ImageView, uri: Uri?, ref: String?) {
        Log.i("WTF", "load ref: $ref  === uri: $uri")
        Log.i("WTF", "load request: $screenWidth x $screenHeight")
        Log.i("WTF", "load view: ${view.width} x ${view.height}")

        if (ref != null && ref != "none") {
            GlideApp.with(view)
                .load(RefUrl(uri.toString(), ref))
                .transition(DrawableTransitionOptions.withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(screenWidth, screenWidth)
                .into(view)
        }
    }
}

data class RefUrl(private val url: String?, private val ref: String) : GlideUrl(url) {
    override fun getCacheKey(): String = ref
}