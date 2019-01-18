package nl.entreco.giddyapp.core.images

import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.request.RequestOptions

class GlideImageLoader(private val screenWidth: Int, private val screenHeight: Int) : ImageLoader {
    override fun get(view: ImageView, uri: Uri?) {
        GlideApp.with(view)
            .load(uri)
            .apply(RequestOptions().override(screenWidth, screenWidth))
            .into(view)
    }
}