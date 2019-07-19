package nl.entreco.giddyapp.libimg.loader.glide

import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import nl.entreco.giddyapp.libimg.R
import nl.entreco.giddyapp.libimg.loader.ImageLoader


internal class GlideImageLoader(private val screenWidth: Int, private val screenHeight: Int) :
    ImageLoader {

    override fun get(view: ImageView, uri: Uri?, ref: String?) {
        if (ref != null && ref != "none") {
            GlideApp.with(view)
                .load(RefUrl(uri.toString(), ref))
                .transition(DrawableTransitionOptions.withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(screenWidth, screenWidth)
                .into(view)
        }
    }

    override fun uri(view: ImageView, uri: Uri?) {
        GlideApp.with(view)
            .load(uri)
            .transition(DrawableTransitionOptions.withCrossFade())
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .override(com.bumptech.glide.request.target.Target.SIZE_ORIGINAL)
            .error(R.drawable.ic_profile)
            .into(view)
    }

    override fun uriBlurred(view: ImageView, uri: Uri?) {
        GlideApp.with(view)
            .load(uri)
            .transition(DrawableTransitionOptions.withCrossFade())
            .transform(BlurTransformation(view.context))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .override(com.bumptech.glide.request.target.Target.SIZE_ORIGINAL)
            .into(view)
    }
}

data class RefUrl(private val url: String?, private val ref: String) : GlideUrl(url) {
    override fun getCacheKey(): String = ref
}