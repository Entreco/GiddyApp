package nl.entreco.giddyapp.creator

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import nl.entreco.giddyapp.libpicker.SelectedImage
import nl.entreco.giddyapp.core.ui.DetailSheet
import nl.entreco.giddyapp.creator.ui.bottom.BottomProgressModel
import nl.entreco.giddyapp.creator.ui.bottom.BottomStepModel
import nl.entreco.giddyapp.creator.ui.entry.EntryModel
import nl.entreco.giddyapp.creator.ui.entry.EntryViewModel
import nl.entreco.giddyapp.libcropper.CropImageView
import nl.entreco.giddyapp.libs.horses.create.CreateHorseRequest
import nl.entreco.giddyapp.libs.horses.create.CreateHorseUsecase
import javax.inject.Inject

class CreatorViewModel @Inject constructor(
    private val createHorseUsecase: CreateHorseUsecase
) : ViewModel(), DetailSheet.SlideListener {

    private val state = MutableLiveData<CreatorState>()
    private val events = MutableLiveData<CreatorState.Event>()
    private val slider = MutableLiveData<Float>()
    val currentState = ObservableField<BottomProgressModel>()
    val currentStep = ObservableField<BottomStepModel>()

    val entryViewModel = ObservableField<EntryViewModel>()

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

    fun toggler(): LiveData<Float> {
        return slider
    }

    fun onSelectFromCamera(){
        postEvent(CreatorState.Event.PickCamera)
    }
    fun onSelectFromGallery(){
        postEvent(CreatorState.Event.PickGallery)
    }
    fun onCropImage(){
        postEvent(CreatorState.Event.Resize)
    }

    fun onProceed() {
        val current = state.value
        when (current) {
            is CreatorState.Entry -> postEvent(CreatorState.Event.Enter)
            is CreatorState.Upload -> go(current.model)
            else -> { }
        }
    }

    override fun onSlide(offset: Float) {
        slider.postValue(offset)
    }

    fun imageSelected(images: List<nl.entreco.giddyapp.libpicker.SelectedImage>) {
        images.firstOrNull()?.let {
            state.postValue(CreatorState.Crop(it))
        }
    }

    fun imageCropped(image: nl.entreco.giddyapp.libpicker.SelectedImage) {
        val entry = CreatorState.Entry(image)
        entryViewModel.set(EntryViewModel(entry))
        state.postValue(entry)
    }

    fun entered(model: EntryModel) {
        state.postValue(CreatorState.Upload(model))
    }

    private fun go(model: EntryModel) {
        createHorseUsecase.go(
            CreateHorseRequest(
                model.horseDetail.name, model.horseDetail.desc, model.horseDetail.gender,
                model.image.uri, model.image.startColor, model.image.endColor
            )
        ){ response ->
            state.postValue(CreatorState.Done(response.horseId))
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
