package nl.entreco.giddyapp.creator.ui.crop

import nl.entreco.giddyapp.libpicker.SelectedImage

sealed class CropImageResult {
    data class Success(val image: SelectedImage) : CropImageResult()
    data class Error(val msg: String) : CropImageResult()
}