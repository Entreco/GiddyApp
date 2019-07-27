package nl.entreco.giddyapp.creator.ui.entry

import androidx.databinding.ObservableField
import androidx.databinding.ObservableFloat
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import nl.entreco.giddyapp.creator.CreatorState
import nl.entreco.giddyapp.creator.R
import nl.entreco.giddyapp.libcore.ChangeableField
import nl.entreco.giddyapp.libhorses.HorseDetail
import nl.entreco.giddyapp.libhorses.HorseRatio
import javax.inject.Inject

class EntryNameViewModel @Inject constructor(
    private val state: CreatorState.EntryName
) : ViewModel() {

    val maxLength = 25
    val constraint = ObservableFloat(0F)
    val image = ChangeableField(state.image.uri)

    val title = ObservableInt(R.string.entry_name)
    val hint = ObservableField("")

    val name = ObservableField("")

    fun compose(): EntryModel = EntryModel(HorseDetail(name.get()!!, "", HorseRatio()), state.image)
}