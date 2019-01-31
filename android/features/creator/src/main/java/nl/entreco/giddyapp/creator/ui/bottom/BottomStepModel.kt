package nl.entreco.giddyapp.creator.ui.bottom

import androidx.databinding.ObservableField
import nl.entreco.giddyapp.creator.CreatorState

data class BottomStepModel(private val state: CreatorState){
    val title = ObservableField<String>(state.title())
    val description = ObservableField<String>(state.description())
}

internal fun CreatorState.title() : String {
    return when(this){
        is CreatorState.Select -> "Select Image"
        is CreatorState.Crop -> "Crop"
        is CreatorState.Upload -> "Save"
        is CreatorState.Done -> "Done"
    }
}

internal fun CreatorState.description() : String {
    return when(this){
        is CreatorState.Select -> "Use your camera, or upload an image"
        is CreatorState.Crop -> "Double check that all details are clearly visible"
        is CreatorState.Upload -> "upload your Horse with all the details"
        is CreatorState.Done -> "Congrats MOFO"
    }
}