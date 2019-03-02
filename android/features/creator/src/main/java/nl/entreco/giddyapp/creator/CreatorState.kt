package nl.entreco.giddyapp.creator

import nl.entreco.giddyapp.creator.ui.entry.EntryModel
import nl.entreco.giddyapp.libpicker.SelectedImage

sealed class CreatorState {

    sealed class Event {
        object PickCamera : Event()
        object PickGallery : Event()
        data class Rejected(val reason: String) : Event()
        object Resize : Event()
        object Enter : Event()
        object Verify : Event()
    }

    object Select : CreatorState()
    data class Crop(val image: SelectedImage) : CreatorState()
    data class Entry(val image: SelectedImage) : CreatorState()
    data class Upload(val model: EntryModel) : CreatorState()
    data class Done(val horseId: String) : CreatorState()
}
