package nl.entreco.giddyapp.core.images

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri

interface ImagePicker {
    fun selectImage()
    fun resize(image: SelectedImage, bmp: Bitmap?, done: (SelectedImage) -> Unit)
    fun get(requestCode: Int, resultCode: Int, data: Intent?, done: (List<SelectedImage>)->Unit)
}