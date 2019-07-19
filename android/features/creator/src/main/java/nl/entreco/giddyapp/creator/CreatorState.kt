package nl.entreco.giddyapp.creator

import nl.entreco.giddyapp.creator.ui.entry.EntryModel
import nl.entreco.giddyapp.libpicker.SelectedImage

sealed class CreatorState {

    sealed class Event {
        object PickCamera : Event()
        object PickGallery : Event()
        data class Rejected(val reason: String) : Event()
        object Resize : Event()
        object EnterName : Event()
        object EnterDesc : Event()
        object EnterGender : Event()
        object EnterPrice : Event()
        object EnterCategory : Event()
        object EnterLevel : Event()
        object Verify : Event()
    }

    object Select : CreatorState()
    data class Crop(val image: SelectedImage) : CreatorState()
    data class EntryName(val image: SelectedImage) : CreatorState()
    data class EntryDescription(val model: EntryModel) : CreatorState()
    data class EntryGender(val model: EntryModel) : CreatorState()
    data class EntryPrice(val model: EntryModel) : CreatorState()
    data class EntryCategory(val model: EntryModel) : CreatorState()
    data class EntryLevel(val model: EntryModel) : CreatorState()
    data class Upload(val model: EntryModel) : CreatorState()
    data class Done(val horseId: String) : CreatorState()
}
