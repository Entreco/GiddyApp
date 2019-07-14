package nl.entreco.giddyapp.creator

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import nl.entreco.giddyapp.creator.ui.bottom.BottomProgressModel
import nl.entreco.giddyapp.creator.ui.entry.EntryModel
import nl.entreco.giddyapp.creator.ui.select.SelectCallback
import nl.entreco.giddyapp.libauth.Authenticator
import nl.entreco.giddyapp.libauth.user.User
import nl.entreco.giddyapp.libcore.toSingleEvent
import nl.entreco.giddyapp.libhorses.create.CreateHorseRequest
import nl.entreco.giddyapp.libhorses.create.CreateHorseUsecase
import nl.entreco.giddyapp.libpicker.SelectedImage
import nl.entreco.giddyapp.libpicker.moderator.Moderation
import java.util.*
import javax.inject.Inject

class CreatorViewModel @Inject constructor(
    private val createHorseUsecase: CreateHorseUsecase,
    private val authenticator: Authenticator
) : ViewModel(), SelectCallback {

    private val stateStack = ArrayDeque<CreatorState>()
    private val state = MutableLiveData<CreatorState>()
    private val events = MutableLiveData<CreatorState.Event>()
    private val snacks = MutableLiveData<String>()
    val currentState = ObservableField<BottomProgressModel>()

    init {
        stateStack.add(CreatorState.Select)
        state.postValue(stateStack.last)
    }

    fun state(): LiveData<CreatorState> = state
    fun events(): LiveData<CreatorState.Event> = events.toSingleEvent()
    fun snacks(): LiveData<String> = snacks.toSingleEvent()

    private fun postEvent(event: CreatorState.Event) {
        events.postValue(event)
    }

    override fun onSelectFromCamera() {
        postEvent(CreatorState.Event.PickCamera)
    }

    override fun onSelectFromGallery() {
        postEvent(CreatorState.Event.PickGallery)
    }

    fun onProceed() {
        when (stateStack.last) {
            is CreatorState.Crop -> postEvent(CreatorState.Event.Resize)
            is CreatorState.EntryName -> postEvent(CreatorState.Event.EnterName)
            is CreatorState.EntryDescription -> postEvent(CreatorState.Event.EnterDesc)
            is CreatorState.EntryGender -> postEvent(CreatorState.Event.EnterGender)
            is CreatorState.EntryPrice -> postEvent(CreatorState.Event.EnterPrice)
            is CreatorState.EntryCategory -> postEvent(CreatorState.Event.EnterCategory)
            is CreatorState.EntryLevel -> postEvent(CreatorState.Event.EnterLevel)
            is CreatorState.Upload -> postEvent(CreatorState.Event.Verify)
            else -> { /* ignore other events */ }
        }
    }

    fun imageRejected(reason: Moderation.Rejected) {
        postEvent(CreatorState.Event.Rejected(reason.contains + reason.missing))
    }

    fun imageSelected(images: List<SelectedImage>) {
        images.firstOrNull()?.let {
            val crop = CreatorState.Crop(it)
            stateStack.add(crop)
            state.postValue(stateStack.last)
        }
    }

    fun imageCropped(image: SelectedImage) {
        val entry = CreatorState.EntryName(image)
        stateStack.add(entry)
        state.postValue(stateStack.last)
    }

    fun enteredName(model: EntryModel) {
        stateStack.add(CreatorState.EntryDescription(model))
        state.postValue(stateStack.last)
    }

    fun enteredDescription(model: EntryModel) {
        stateStack.add(CreatorState.EntryGender(model))
        state.postValue(stateStack.last)
    }

    fun enteredGender(model: EntryModel) {
        stateStack.add(CreatorState.EntryPrice(model))
        state.postValue(stateStack.last)
    }

    fun enteredPrice(model: EntryModel) {
        stateStack.add(CreatorState.EntryCategory(model))
        state.postValue(stateStack.last)
    }

    fun enteredCategory(model: EntryModel) {
        stateStack.add(CreatorState.EntryLevel(model))
        state.postValue(stateStack.last)
    }

    fun entered(model: EntryModel) {
        stateStack.add(CreatorState.Upload(model))
        state.postValue(stateStack.last)
    }

    fun startUpload(model: EntryModel) {
        authenticator.current { user ->
            when (user) {
                is User.Authenticated -> uploadForUser(user.uid, model)
                is User.Anomymous -> snacks.postValue("You need to be logged in to upload horses")
                else -> { /* Show Error login */ }
            }
        }
    }

    private fun uploadForUser(uid: String, model: EntryModel) {
        createHorseUsecase.go(
            CreateHorseRequest(
                uid,
                model.horseDetail.name, model.horseDetail.desc, model.horseDetail.gender,
                model.horseDetail.price, model.horseDetail.category, model.horseDetail.type,
                model.image.uri, model.image.startColor, model.image.endColor
            )
        ) { response ->
            val done = CreatorState.Done(response.horseId)
            stateStack.add(done)
            state.postValue(stateStack.last)
        }
    }

    fun popSate(): Boolean {
        return if (stateStack.size > 1) {
            stateStack.removeLast()
            true
        } else {
            false
        }
    }
}
