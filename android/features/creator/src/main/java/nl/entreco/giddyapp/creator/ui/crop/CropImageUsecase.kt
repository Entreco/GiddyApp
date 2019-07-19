package nl.entreco.giddyapp.creator.ui.crop

import nl.entreco.giddyapp.libcore.onBg
import nl.entreco.giddyapp.libpicker.ImagePicker
import javax.inject.Inject

class CropImageUsecase @Inject constructor(
    private val picker: ImagePicker
) {

    fun go(request: CropImageRequest, result: (CropImageResult) -> Unit) {
        onBg {
            request.image.let { img ->
                request.view.setOnCropImageCompleteListener { result ->
                    if (result.isSuccessful) {
                        picker.resize(img, result.bitmap!!) { img ->
                            result(CropImageResult.Success(img))
                        }
                    } else {
                        result(CropImageResult.Error(result.error?.localizedMessage ?: "unknown error"))
                    }
                }
                request.view.getCroppedImageAsync()
            }
        }
    }
}