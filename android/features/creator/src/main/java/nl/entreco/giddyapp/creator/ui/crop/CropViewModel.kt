package nl.entreco.giddyapp.creator.ui.crop

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import nl.entreco.giddyapp.core.ChangeableField
import nl.entreco.giddyapp.core.images.ImagePicker
import nl.entreco.giddyapp.core.images.SelectedImage
import nl.entreco.giddyapp.creator.CreatorState
import nl.entreco.giddyapp.libcropper.CropImageView
import javax.inject.Inject

class CropViewModel @Inject constructor(
    private val picker: ImagePicker,
    private val crop: CreatorState.Crop
) : ViewModel() {

    val image = ChangeableField(crop.image.uri)
    val isCropping = ObservableBoolean(false)

    private val event = MutableLiveData<CreatorState.Event>()
    fun events(): LiveData<CreatorState.Event> {
        return event
    }

    fun resize(imageView: CropImageView, done: (SelectedImage) -> Unit) {
        crop.image.let { img ->
            isCropping.set(true)
            imageView.setOnCropImageCompleteListener { result ->
                if(result.isSuccessful) {
                    picker.resize(img, result.bitmap, done)
                }
                else Log.e("OOPS", "error cropping: ${result.error}")

                isCropping.set(false)
            }

            imageView.getCroppedImageAsync()
        }
    }
}