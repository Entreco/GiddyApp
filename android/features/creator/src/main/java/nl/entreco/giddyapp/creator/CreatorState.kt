package nl.entreco.giddyapp.creator

import android.net.Uri
import nl.entreco.giddyapp.core.images.SelectedImage

sealed class CreatorState(val index: Int) {

    sealed class Event {
        object Pick : Event()
        object Resize : Event()
        object Upload : Event()
        object None : Event()
    }

    object Select : CreatorState(0)
    data class Crop(val image: SelectedImage) : CreatorState(1)
    data class Upload(val uri: Uri) : CreatorState(2)
    object Done : CreatorState(3)
}
