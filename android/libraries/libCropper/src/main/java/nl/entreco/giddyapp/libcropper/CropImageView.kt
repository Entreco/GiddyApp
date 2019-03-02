package nl.entreco.giddyapp.libcropper

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import kotlin.random.Random


class CropImageView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    private val libCropImageView = com.theartofdev.edmodo.cropper.CropImageView(context, attrs)

    init {
        libCropImageView.id = Random.nextInt(10000)
        addView(libCropImageView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    var cropShape = CropShape.RECTANGLE
        get() = when (libCropImageView.cropShape) {
            com.theartofdev.edmodo.cropper.CropImageView.CropShape.RECTANGLE -> CropShape.RECTANGLE
            com.theartofdev.edmodo.cropper.CropImageView.CropShape.OVAL -> CropShape.OVAL
            else -> CropShape.RECTANGLE
        }
        set(value) {
            field = value
            val internal = when (value) {
                CropShape.RECTANGLE -> com.theartofdev.edmodo.cropper.CropImageView.CropShape.RECTANGLE
                CropShape.OVAL -> com.theartofdev.edmodo.cropper.CropImageView.CropShape.OVAL
            }
            libCropImageView.setCropShape(internal)
        }

    enum class CropShape {
        RECTANGLE,
        OVAL
    }

    fun setImageUriAsync(uri: Uri?) {
        libCropImageView.setImageUriAsync(uri)
    }

    fun setSnapRadius(radius: Float) {
        libCropImageView.setSnapRadius(radius)
    }

    fun setMultiTouchEnabled(enabled: Boolean) {
        libCropImageView.setMultiTouchEnabled(enabled)
    }

    fun setFixedAspectRatio(fixed: Boolean) {
        libCropImageView.setFixedAspectRatio(fixed)
    }

    fun setMinCropSize(minimumSize: Int){
        libCropImageView.setMinCropResultSize(minimumSize, minimumSize)
    }

    fun setAspectRatio(x: Int, y: Int) {
        libCropImageView.setAspectRatio(x, y)
    }

    fun setOnCropImageCompleteListener(done: (Result) -> Unit) {
        libCropImageView.setOnCropImageCompleteListener { _, result ->
            done(
                Result(
                    result?.isSuccessful ?: false,
                    result?.bitmap,
                    result?.error
                )
            )
        }
    }

    fun getCroppedImageAsync() {
        libCropImageView.getCroppedImageAsync()
    }

    data class Result(val isSuccessful: Boolean, val bitmap: Bitmap?, val error: Exception?)
}