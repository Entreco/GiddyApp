package nl.entreco.giddyapp.libpicker

import android.content.Intent
import android.graphics.Bitmap

interface ImagePicker {

    companion object {
        const val REQ_CAMERA = 42
        const val REQ_GALLERY = 69
    }

    fun selectImage(fromCamera: Boolean)
    fun resize(image: SelectedImage, bmp: Bitmap?, done: (SelectedImage) -> Unit)
    fun get(requestCode: Int, resultCode: Int, data: Intent?, done: (List<SelectedImage>)->Unit)
}