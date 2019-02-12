package nl.entreco.giddyapp.creator

import nl.entreco.giddyapp.creator.ui.entry.EntryModel
import nl.entreco.giddyapp.libpicker.SelectedImage

sealed class CreatorState(val index: Int) {

    sealed class Event {
        object PickCamera : Event()
        object PickGallery : Event()
        object Resize : Event()
        object Enter : Event()
        object Verify : Event()
    }

    object Select : CreatorState(0)
    data class Crop(val image: SelectedImage) : CreatorState(1)
    data class Entry(val image: SelectedImage) : CreatorState(2)
    data class Upload(val model: EntryModel) : CreatorState(3)
    data class Done(val horseId: String) : CreatorState(4)
}
