package nl.entreco.giddyapp.creator.ui.select

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import nl.entreco.giddyapp.creator.CreatorState
import javax.inject.Inject

class SelectViewModel @Inject constructor(private val select: CreatorState.Select?) : ViewModel() {
    private val event = MutableLiveData<CreatorState.Event>()
    fun events(): LiveData<CreatorState.Event> {
        return event
    }
}