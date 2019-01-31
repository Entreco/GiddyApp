package nl.entreco.giddyapp.creator.ui.entry

import androidx.databinding.ObservableField
import androidx.databinding.ObservableFloat
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import nl.entreco.giddyapp.core.ChangeableField
import nl.entreco.giddyapp.creator.CreatorState
import nl.entreco.giddyapp.libs.horses.*
import javax.inject.Inject

class EntryViewModel @Inject constructor(private val state: CreatorState.Entry) : ViewModel() {

    val constraint = ObservableFloat(0F)
    val image = ChangeableField(state.image.uri)

    val name = ObservableField<String>("")
    val desc = ObservableField<String>("")
    val gender = ObservableField<HorseGender>(HorseGender.Unknown)
    val level = ObservableField<HorseLevel>(HorseLevel.Unknown)
    val category = ObservableField<HorseCategory>(HorseCategory.Unknown)
    val price = ObservableField<HorsePrice>(HorsePrice.Unknown)

    private val event = MutableLiveData<CreatorState.Event>()
    fun events(): LiveData<CreatorState.Event> {
        return event
    }

    fun compose(done: (EntryModel) -> Unit) {
        if (validate()) {
            val details = HorseDetail(name.get()!!, desc.get()!!, gender.get()!!, level.get()!!, category.get()!!, price.get()!!)
            val entry = EntryModel(details, state.image)
            done(entry)
        }
    }

    private fun validate(): Boolean = true
}