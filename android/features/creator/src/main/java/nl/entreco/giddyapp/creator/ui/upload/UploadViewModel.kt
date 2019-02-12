package nl.entreco.giddyapp.creator.ui.upload

import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.databinding.ObservableFloat
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import nl.entreco.giddyapp.core.ChangeableField
import nl.entreco.giddyapp.creator.CreatorState
import nl.entreco.giddyapp.creator.R
import nl.entreco.giddyapp.creator.ui.entry.EntryModel
import nl.entreco.giddyapp.creator.ui.select.SelectBottomModel
import javax.inject.Inject

class UploadViewModel @Inject constructor(private val state: CreatorState.Upload): ViewModel() {

    val constraint = ObservableFloat(0F)
    val image = ChangeableField(state.model.image.uri)
    val name = ObservableField<String>(state.model.horseDetail.name)
    val desc = ObservableField<String>(state.model.horseDetail.desc)
    val level = ObservableField<String>(state.model.horseDetail.type.toString())
    val price = ObservableField<String>(state.model.horseDetail.price.toString())
    val category = ObservableField<String>(state.model.horseDetail.category.toString())
    val genderIcon = ObservableInt(0)

    fun model(): EntryModel = state.model
}