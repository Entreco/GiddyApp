package nl.entreco.giddyapp.creator

import androidx.annotation.DrawableRes
import nl.entreco.giddyapp.libpicker.SelectedImage
import nl.entreco.giddyapp.creator.ui.entry.EntryModel

sealed class CreatorState(val index: Int) {

    sealed class Event {
        object Pick : Event()
        object Resize : Event()
        object Enter : Event()
    }

    object Select : CreatorState(0)
    data class Crop(val image: nl.entreco.giddyapp.libpicker.SelectedImage) : CreatorState(1)
    data class Entry(val image: nl.entreco.giddyapp.libpicker.SelectedImage) : CreatorState(2)
    data class Upload(val model: EntryModel) : CreatorState(3)
    data class Done(val horseId: String) : CreatorState(4)
}

internal fun CreatorState.title() : String {
    return when(this){
        is CreatorState.Select -> "Select Image"
        is CreatorState.Crop -> "Crop"
        is CreatorState.Entry -> "Prepare"
        is CreatorState.Upload -> "Save"
        is CreatorState.Done -> "Done"
    }
}

internal fun CreatorState.description() : String {
    return when(this){
        is CreatorState.Select -> "Use your camera, or upload an image"
        is CreatorState.Crop -> "Double check that all details are clearly visible"
        is CreatorState.Entry -> "Enter as much details about your horse as possible"
        is CreatorState.Upload -> "Upload your Horse with all the details"
        is CreatorState.Done -> "Congrats MOFO"
    }
}

@DrawableRes
internal fun CreatorState.icon() : Int {
    return when(this){
        is CreatorState.Select -> R.drawable.ic_select
        is CreatorState.Crop -> R.drawable.ic_resize
        is CreatorState.Entry -> R.drawable.ic_entry
        is CreatorState.Upload -> R.drawable.ic_upload
        is CreatorState.Done -> 0
    }
}
