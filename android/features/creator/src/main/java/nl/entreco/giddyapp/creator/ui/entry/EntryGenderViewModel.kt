package nl.entreco.giddyapp.creator.ui.entry

import androidx.annotation.IdRes
import androidx.databinding.ObservableField
import androidx.databinding.ObservableFloat
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import nl.entreco.giddyapp.creator.CreatorState
import nl.entreco.giddyapp.creator.R
import nl.entreco.giddyapp.libcore.ChangeableField
import nl.entreco.giddyapp.libhorses.HorseGender
import javax.inject.Inject

class EntryGenderViewModel @Inject constructor(
    private val state: CreatorState.EntryGender
) : ViewModel() {

    fun compose(): EntryModel =
        state.model.copy(horseDetail = state.model.horseDetail.copy(gender = HorseGender.values()[gender.get()]))

    val constraint = ObservableFloat(0F)
    val image = ChangeableField(state.model.image.uri)

    val name = ObservableField<String>(state.model.horseDetail.name)
    val gender = ObservableInt(HorseGender.Unknown.ordinal)
    val checked = ObservableInt(0)

    fun checked(@IdRes itemId: Int) {
        checked.set(itemId)
        when (itemId) {
            R.id.stallion -> gender.set(HorseGender.Stallion.ordinal)
            R.id.mare -> gender.set(HorseGender.Mare.ordinal)
            R.id.gelding -> gender.set(HorseGender.Gelding.ordinal)
            else -> gender.set(HorseGender.Unknown.ordinal)
        }
    }
}