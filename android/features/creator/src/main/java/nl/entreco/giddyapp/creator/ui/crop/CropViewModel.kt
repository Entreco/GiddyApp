package nl.entreco.giddyapp.creator.ui.crop

import android.util.Log
import androidx.databinding.ObservableFloat
import androidx.lifecycle.ViewModel
import nl.entreco.giddyapp.core.ChangeableField
import nl.entreco.giddyapp.creator.CreatorState
import nl.entreco.giddyapp.libcropper.CropImageView
import nl.entreco.giddyapp.libpicker.SelectedImage
import javax.inject.Inject

class CropViewModel @Inject constructor(
    private val crop: CreatorState.Crop,
    private val cropImageUsecase: CropImageUsecase
) : ViewModel() {

    val step = CropBottomModel(crop)
    val image = ChangeableField(crop.image.uri)

    fun resize(imageView: CropImageView, done: (SelectedImage) -> Unit) {
        cropImageUsecase.go(CropImageRequest(imageView, crop.image)) { result ->
            when (result) {
                is CropImageResult.Success -> done(result.image)
                is CropImageResult.Error -> Log.e("Oops", "err: ${result.msg?.localizedMessage}")
            }
        }
    }
}