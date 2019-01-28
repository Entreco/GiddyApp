package nl.entreco.giddyapp.creator

import android.net.Uri
import nl.entreco.giddyapp.core.images.SelectedImage

sealed class CreatorState {

    sealed class Event {
        object Pick : Event()
        object Resize : Event()
        object Upload : Event()
    }

    object Select : CreatorState()
    data class Crop(val image: SelectedImage) : CreatorState()
    data class Upload(val uri: Uri) : CreatorState()
}
