package nl.entreco.giddyapp.core.images

import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

class GlideImageLoader(private val screenWidth: Int, private val screenHeight: Int) : ImageLoader {
    override fun get(view: ImageView, uri: Uri?) {
        GlideApp.with(view)
            .load(uri)
            .transition(DrawableTransitionOptions.withCrossFade())
            .apply(RequestOptions().override(screenWidth, screenWidth))
            .into(view)
    }
}