package nl.entreco.giddyapp.creator.ui.bottom

import androidx.databinding.ObservableField
import nl.entreco.giddyapp.creator.CreatorState
import nl.entreco.giddyapp.creator.description
import nl.entreco.giddyapp.creator.title

data class BottomStepModel(val state: CreatorState){
    val title = ObservableField<String>(state.title())
    val description = ObservableField<String>(state.description())
}