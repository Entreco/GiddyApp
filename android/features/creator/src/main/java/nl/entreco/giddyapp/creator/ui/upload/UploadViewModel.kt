package nl.entreco.giddyapp.creator.ui.upload

import android.net.Uri
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import nl.entreco.giddyapp.core.ChangeableField
import nl.entreco.giddyapp.creator.CreatorState
import nl.entreco.giddyapp.libs.horses.create.CreateHorseRequest
import javax.inject.Inject

class UploadViewModel @Inject constructor(private val state: CreatorState.Upload?): ViewModel() {

    val isValid = ObservableBoolean(false)
    val image = ChangeableField<Uri>(state?.uri)

    private val event = MutableLiveData<CreatorState.Event>()
    fun events(): LiveData<CreatorState.Event> {
        return event
    }

    fun tryUpload() {
        event.postValue(CreatorState.Event.Upload)
//        if (validInput()) {
//            createHorseUsecase.go(
//                CreateHorseRequest(
//                    name.get()!!,
//                    description.get()!!,
//                    gender.get()!!,
//                    image.get()!!,
//                    startColor,
//                    endColor
//                )
//            ) {
//                // Bingo or not
//            }
//        }
    }
}