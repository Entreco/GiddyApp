package nl.entreco.giddyapp.creator

import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import nl.entreco.giddyapp.creator.ui.bottom.BottomProgressModel
import nl.entreco.giddyapp.creator.ui.entry.EntryModel
import nl.entreco.giddyapp.creator.ui.entry.EntryViewModel
import nl.entreco.giddyapp.creator.ui.select.SelectCallback
import nl.entreco.giddyapp.libcropper.CropImageView
import nl.entreco.giddyapp.libpicker.SelectedImage
import nl.entreco.giddyapp.libs.horses.HorseGender
import nl.entreco.giddyapp.libs.horses.create.CreateHorseRequest
import nl.entreco.giddyapp.libs.horses.create.CreateHorseUsecase
import java.util.*
import javax.inject.Inject

class CreatorViewModel @Inject constructor(
    private val createHorseUsecase: CreateHorseUsecase
) : ViewModel(), SelectCallback {

    private val stateStack = ArrayDeque<CreatorState>()
    private val state = MutableLiveData<CreatorState>()
    private val events = MutableLiveData<CreatorState.Event>()
    val currentState = ObservableField<BottomProgressModel>()

    init {
        stateStack.add(CreatorState.Select)
        state.postValue(stateStack.last)
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

    override fun onSelectFromCamera() {
        postEvent(CreatorState.Event.PickCamera)
    }

    override fun onSelectFromGallery() {
        postEvent(CreatorState.Event.PickGallery)
    }

    fun onProceed() {
        val current = stateStack.last
        when (current) {
            is CreatorState.Crop -> postEvent(CreatorState.Event.Resize)
            is CreatorState.Entry -> postEvent(CreatorState.Event.Enter)
            is CreatorState.Upload -> postEvent(CreatorState.Event.Verify)
            else -> { /* ignore other events */ }
        }
    }

    fun imageSelected(images: List<SelectedImage>) {
        images.firstOrNull()?.let {
            val crop = CreatorState.Crop(it)
            stateStack.add(crop)
            state.postValue(stateStack.last)
        }
    }

    fun imageCropped(image: SelectedImage) {
        val entry = CreatorState.Entry(image)
        stateStack.add(entry)
        state.postValue(stateStack.last)
    }

    fun entered(model: EntryModel) {
        stateStack.add(CreatorState.Upload(model))
        state.postValue(stateStack.last)
    }

    fun startUpload(model: EntryModel) {
        createHorseUsecase.go(
            CreateHorseRequest(
                model.horseDetail.name, model.horseDetail.desc, model.horseDetail.gender,
                model.image.uri, model.image.startColor, model.image.endColor
            )
        ) { response ->
            val done = CreatorState.Done(response.horseId)
            stateStack.add(done)
            state.postValue(stateStack.last)
        }
    }

    fun popSate() {
        if (stateStack.isNotEmpty()) {
            stateStack.removeLast()

            if(stateStack.isNotEmpty()) state.postValue(stateStack.last)
        }
    }

    companion object {

        @JvmStatic
        @BindingAdapter("ga_preview")
        fun preview(view: CropImageView, uri: Uri?) {
            view.setAspectRatio(1, 1)
            view.setFixedAspectRatio(true)
            view.setMultiTouchEnabled(false)
            view.setSnapRadius(10f)
            view.setImageUriAsync(uri)
            view.cropShape = CropImageView.CropShape.RECTANGLE
        }

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
