package nl.entreco.giddyapp.creator.ui.select

import android.view.View
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import nl.entreco.giddyapp.creator.CreatorState
import javax.inject.Inject

class SelectViewModel @Inject constructor(private val select: CreatorState.Select?) : ViewModel() {

    val showImage = ObservableInt(View.VISIBLE)

    private val event = MutableLiveData<CreatorState.Event>()
    fun events(): LiveData<CreatorState.Event> {
        return event
    }

    fun selectImage() {
        event.postValue(CreatorState.Event.Pick)
    }
}