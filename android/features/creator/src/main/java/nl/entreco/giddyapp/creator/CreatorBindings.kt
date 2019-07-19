package nl.entreco.giddyapp.creator

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import nl.entreco.giddyapp.libcropper.CropImageView

object CreatorBindings {

    @JvmStatic
    @BindingAdapter("ga_preview")
    fun preview(view: CropImageView, uri: Uri?) {
        view.setAspectRatio(1, 1)
        view.setMinCropSize(1050)
        view.setFixedAspectRatio(true)
        view.setMultiTouchEnabled(false)
        view.setSnapRadius(10f)
        view.setImageUriAsync(uri)
        view.cropShape = CropImageView.CropShape.RECTANGLE
    }

    @JvmStatic
    @BindingAdapter("ga_preview")
    fun preview(view: ImageView, uri: Uri?) {
        view.setImageURI(null)
        if (uri != null) {
            view.setImageURI(uri)
        }
    }
}
