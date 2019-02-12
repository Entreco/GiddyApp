package nl.entreco.giddyapp.creator.ui.crop

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableFloat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import nl.entreco.giddyapp.core.ChangeableField
import nl.entreco.giddyapp.libpicker.ImagePicker
import nl.entreco.giddyapp.creator.CreatorState
import nl.entreco.giddyapp.creator.ui.select.SelectBottomModel
import nl.entreco.giddyapp.libcropper.CropImageView
import javax.inject.Inject

class CropViewModel @Inject constructor(
    private val picker: ImagePicker,
    private val crop: CreatorState.Crop
) : ViewModel() {

    val step = CropBottomModel(crop)
    val image = ChangeableField(crop.image.uri)
    val isCropping = ObservableBoolean(false)

    fun resize(imageView: CropImageView, done: (nl.entreco.giddyapp.libpicker.SelectedImage) -> Unit) {
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