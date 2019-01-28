package nl.entreco.giddyapp.creator

import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import nl.entreco.giddyapp.core.ChangeableField
import nl.entreco.giddyapp.core.LaunchHelper
import nl.entreco.giddyapp.core.images.ImagePicker
import nl.entreco.giddyapp.core.images.SelectedImage
import nl.entreco.giddyapp.libs.horses.HorseGender
import nl.entreco.giddyapp.libs.horses.create.CreateHorseRequest
import nl.entreco.giddyapp.libs.horses.create.CreateHorseUsecase
import javax.inject.Inject

class CreatorViewModel @Inject constructor(
    private val createHorseUsecase: CreateHorseUsecase
) : ViewModel() {

    private val state = MutableLiveData<CreatorState>()
    private val events = MutableLiveData<CreatorState.Event>()

    init {
        state.postValue(CreatorState.Select)
    }

    fun state() : LiveData<CreatorState>{
        return state
    }

    fun events() : LiveData<CreatorState.Event>{
        return events
    }

    fun postEvent(event: CreatorState.Event) {
        events.postValue(event)
    }

    fun imageSelected(images: List<SelectedImage>) {
        images.firstOrNull()?.let {
            state.postValue(CreatorState.Crop(it))
        }
    }

    companion object {
        @JvmStatic
        @BindingAdapter("ga_preview")
        fun preview(view: ImageView, uri: Uri?){
            view.setImageURI(null)
            if(uri != null) {
                view.setImageURI(uri)
            }
        }
    }
}
