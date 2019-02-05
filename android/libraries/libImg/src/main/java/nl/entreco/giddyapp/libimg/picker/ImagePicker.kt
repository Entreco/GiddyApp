package nl.entreco.giddyapp.libimg.picker

import android.content.Intent
import android.graphics.Bitmap
import nl.entreco.giddyapp.libimg.SelectedImage

interface ImagePicker {
    fun selectImage()
    fun resize(image: SelectedImage, bmp: Bitmap?, done: (SelectedImage) -> Unit)
    fun get(requestCode: Int, resultCode: Int, data: Intent?, done: (List<SelectedImage>)->Unit)
}