package nl.entreco.giddyapp.libimg.loader

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import nl.entreco.giddyapp.libimg.R

interface ImageLoader {
    fun get(view: ImageView, uri: Uri?, ref: String?)
    fun uri(view: ImageView, uri: Uri?)
    fun uriBlurred(view: ImageView, uri: Uri?)
}

object ImageBindings {

    @JvmStatic
    @BindingAdapter("ga_loader", "ga_image", "ga_ref", requireAll = true)
    fun loadImageFromFirebase(view: ImageView, loader: ImageLoader?, uri: Uri?, ref: String?) {
        if (view.drawable == null) {
            if(ref == "loading") view.setImageResource(nl.entreco.giddyapp.libcore.R.drawable.empty)
            else if(ref == "notFound") view.setImageResource(nl.entreco.giddyapp.libcore.R.drawable.empty)
            else {
                loader?.get(view, uri, ref)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("ga_loader", "ga_image", requireAll = true)
    fun loadImage(view: ImageView, loader: ImageLoader?, uri: Uri?) {
        loader?.uri(view, uri)
    }

    @JvmStatic
    @BindingAdapter("ga_loader", "ga_blurImage", requireAll = true)
    fun loadImageBlurred(view: ImageView, loader: ImageLoader?, uri: Uri?) {
        if (view.drawable == null) {
            loader?.uriBlurred(view, uri)
        }
    }
}