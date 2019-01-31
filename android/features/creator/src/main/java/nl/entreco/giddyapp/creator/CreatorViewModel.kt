package nl.entreco.giddyapp.creator

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import nl.entreco.giddyapp.core.images.SelectedImage
import nl.entreco.giddyapp.creator.ui.bottom.BottomProgressModel
import nl.entreco.giddyapp.creator.ui.bottom.BottomStepModel
import nl.entreco.giddyapp.libs.horses.create.CreateHorseUsecase
import javax.inject.Inject

class CreatorViewModel @Inject constructor(
    private val createHorseUsecase: CreateHorseUsecase
) : ViewModel() {

    private val state = MutableLiveData<CreatorState>()
    private val events = MutableLiveData<CreatorState.Event>()
    val currentState = ObservableField<BottomProgressModel>()
    val currentStep = ObservableField<BottomStepModel>()

    init {
        state.postValue(CreatorState.Select)
    }

    fun state(): LiveData<CreatorState> {
        return state
    }

    fun events(): LiveData<CreatorState.Event> {
        return events
    }

    fun postEvent(event: CreatorState.Event) {
        events.postValue(event)
    }

    fun onProceed() {
        val current = state.value
        when (current) {
            is CreatorState.Select -> postEvent(CreatorState.Event.Pick)
            is CreatorState.Crop -> postEvent(CreatorState.Event.Resize)
            is CreatorState.Upload -> state.postValue(CreatorState.Done)
            else -> {}
        }
    }

    fun imageSelected(images: List<SelectedImage>) {
        images.firstOrNull()?.let {
            state.postValue(CreatorState.Crop(it))
        }
    }

    fun imageCropped(it: SelectedImage) {
        state.postValue(CreatorState.Upload(it.uri))
    }

    companion object {
        @JvmStatic
        @BindingAdapter("ga_preview")
        fun preview(view: ImageView, uri: Uri?) {
            view.setImageURI(null)
            if (uri != null) {
                view.setImageURI(uri)
            }
        }
    }
}
