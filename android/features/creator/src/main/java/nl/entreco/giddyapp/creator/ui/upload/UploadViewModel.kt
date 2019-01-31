package nl.entreco.giddyapp.creator.ui.upload

import android.net.Uri
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableFloat
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import nl.entreco.giddyapp.core.ChangeableField
import nl.entreco.giddyapp.creator.CreatorState
import nl.entreco.giddyapp.libs.horses.create.CreateHorseRequest
import javax.inject.Inject

class UploadViewModel @Inject constructor(private val state: CreatorState.Upload): ViewModel() {

    val image = ChangeableField(state.model.image.uri)
    val constraint = ObservableFloat(0F)

    private val event = MutableLiveData<CreatorState.Event>()
    fun events(): LiveData<CreatorState.Event> {
        return event
    }
}