package nl.entreco.giddyapp.creator

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class CreatorViewModel@Inject constructor() : ViewModel(){

    val details = ObservableField<String>(instantOrNot())

    private fun instantOrNot(): String {
        return "Fo shizzle my dizzle"
    }
}