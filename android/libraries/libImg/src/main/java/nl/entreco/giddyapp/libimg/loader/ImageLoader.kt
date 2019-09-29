package nl.entreco.giddyapp.libimg.loader

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import nl.entreco.giddyapp.libimg.R

interface ImageLoader {

    fun get(view: ImageView, uri: Uri?, ref: String?)
    fun getBlurred(view: ImageView, uri: Uri?, ref: String?)
    fun uri(view: ImageView, uri: Uri?)
    fun uriBlurred(view: ImageView, uri: Uri?)
    fun clear(view: ImageView)
}

object ImageBindings {

    @JvmStatic
    @BindingAdapter("ga_loader", "ga_image", "ga_ref", requireAll = true)
    fun loadImageFromFirebase(view: ImageView, loader: ImageLoader?, uri: Uri?, ref: String?) {
        if (view.drawable == null || tagChanged(view, ref)) {
            loader?.get(view, uri, ref)
            view.setTag(R.id.glide_custom_view_target_tag, ref)
        }
    }

    @JvmStatic
    @BindingAdapter("ga_loader", "ga_blurImage", "ga_ref", requireAll = true)
    fun loadImageBlurredFromFirebase(view: ImageView, loader: ImageLoader?, uri: Uri?, ref: String?) {
        if (view.drawable == null || tagChanged(view, ref)) {
            loader?.getBlurred(view, uri, ref)
            view.setTag(R.id.glide_custom_view_target_tag, ref)
        }
    }

    private fun tagChanged(view: ImageView, ref: String?) =
        (view.getTag(R.id.glide_custom_view_target_tag) as? String) != ref

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