package nl.entreco.giddyapp.history

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import nl.entreco.giddyapp.libhorses.Horse
import javax.inject.Inject

class HistoryHeaderViewModel @Inject constructor() : ViewModel() {

    val image = ObservableField(Horse.Loading.imageUri)
    val reference = ObservableField<String>(Horse.Loading.imageRef)
    val name = ObservableField<String>("")

    fun show(horse: Horse) {
        image.set(horse.imageUri)
        reference.set(horse.imageRef)
        name.set(horse.name)
    }
}