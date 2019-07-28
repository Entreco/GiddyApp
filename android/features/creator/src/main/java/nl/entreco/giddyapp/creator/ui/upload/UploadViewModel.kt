package nl.entreco.giddyapp.creator.ui.upload

import androidx.databinding.ObservableField
import androidx.databinding.ObservableFloat
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import nl.entreco.giddyapp.creator.CreatorState
import nl.entreco.giddyapp.creator.ui.entry.EntryModel
import nl.entreco.giddyapp.libcore.ChangeableField
import nl.entreco.giddyapp.libhorses.icon
import javax.inject.Inject

class UploadViewModel @Inject constructor(private val state: CreatorState.Upload) : ViewModel() {

    val constraint = ObservableFloat(0F)
    val image = ChangeableField(state.model.image.uri)
    val name = ObservableField(state.model.horseDetail.name)
    val desc = ObservableField(state.model.horseDetail.desc)
    val level = ObservableField(state.model.horseDetail.type)
    val price = ObservableField(state.model.horseDetail.price)
    val category = ObservableField(state.model.horseDetail.category)
    val genderIcon = ObservableInt(state.model.horseDetail.gender.icon())

    fun model(): EntryModel = state.model
}