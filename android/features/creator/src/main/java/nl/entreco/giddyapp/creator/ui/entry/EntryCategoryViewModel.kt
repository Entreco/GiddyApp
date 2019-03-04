package nl.entreco.giddyapp.creator.ui.entry

import androidx.databinding.ObservableField
import androidx.databinding.ObservableFloat
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import nl.entreco.giddyapp.creator.CreatorState
import nl.entreco.giddyapp.libcore.ChangeableField
import nl.entreco.giddyapp.libhorses.HorseCategory
import javax.inject.Inject

class EntryCategoryViewModel @Inject constructor(
    private val state: CreatorState.EntryCategory
) : ViewModel() {

    fun compose(): EntryModel =
        state.model.copy(horseDetail = state.model.horseDetail.copy(category = HorseCategory.values()[progress.get()]))

    val constraint = ObservableFloat(0F)
    val image = ChangeableField(state.model.image.uri)

    val name = ObservableField<String>(state.model.horseDetail.name)
    val progress = ObservableInt(0)
}