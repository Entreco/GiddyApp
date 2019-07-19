package nl.entreco.giddyapp.creator.ui.crop

import nl.entreco.giddyapp.libcropper.CropImageView
import nl.entreco.giddyapp.libpicker.SelectedImage

data class CropImageRequest(val view: CropImageView, val image: SelectedImage)