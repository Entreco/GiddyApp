package nl.entreco.giddyapp.libpicker

import nl.entreco.giddyapp.libpicker.moderator.Moderation

sealed class ImagePickerResult{
    data class Success(val images: List<SelectedImage>) : ImagePickerResult()
    data class Failed(val reason: Moderation.Rejected) : ImagePickerResult()
}