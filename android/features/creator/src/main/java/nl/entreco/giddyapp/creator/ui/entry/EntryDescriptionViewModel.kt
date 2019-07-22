package nl.entreco.giddyapp.creator.ui.entry

import androidx.databinding.ObservableField
import androidx.databinding.ObservableFloat
import androidx.lifecycle.ViewModel
import nl.entreco.giddyapp.creator.CreatorState
import nl.entreco.giddyapp.libcore.ChangeableField
import javax.inject.Inject

class EntryDescriptionViewModel @Inject constructor(
    private val state: CreatorState.EntryDescription
) : ViewModel() {

    val maxLength = 100
    val constraint = ObservableFloat(0F)

    val image = ChangeableField(state.model.image.uri)
    val hint = ObservableField("")
    val name = ObservableField(state.model.horseDetail.name)
    val desc = ObservableField(state.model.horseDetail.desc)

    fun compose(): EntryModel = state.model.copy(horseDetail = state.model.horseDetail.copy(desc = desc.get()!!))
}