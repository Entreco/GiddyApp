package nl.entreco.giddyapp.core.images

import android.content.Intent

interface ImagePicker {
    fun selectImage()
    fun get(requestCode: Int, resultCode: Int, data: Intent?, done: (List<SelectedImage>)->Unit)
}