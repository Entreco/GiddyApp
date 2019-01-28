package nl.entreco.giddyapp.creator.ui.crop

import android.net.Uri
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import nl.entreco.giddyapp.core.ChangeableField
import nl.entreco.giddyapp.creator.CreatorState
import javax.inject.Inject

class CropViewModel @Inject constructor(private val crop: CreatorState.Crop?) : ViewModel() {

    val isValid = ObservableBoolean(false)
    val showImage = ObservableInt(View.VISIBLE)
    val image = ChangeableField<Uri>(crop?.image?.uri)

    private val event = MutableLiveData<CreatorState.Event>()
    fun events(): LiveData<CreatorState.Event> {
        return event
    }

    fun cropImage() {
        event.postValue(CreatorState.Event.Resize)
    }
}