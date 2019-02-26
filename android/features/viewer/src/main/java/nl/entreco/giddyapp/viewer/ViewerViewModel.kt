package nl.entreco.giddyapp.viewer

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import nl.entreco.giddyapp.core.ui.DetailSheet
import nl.entreco.giddyapp.libs.horses.Horse
import nl.entreco.giddyapp.libs.horses.fetch.FetchHorseRequest
import nl.entreco.giddyapp.libs.horses.fetch.FetchHorseResponse
import nl.entreco.giddyapp.libs.horses.fetch.FetchHorseUsecase
import nl.entreco.giddyapp.libs.horses.swap.SwapHorseUsecase
import nl.entreco.giddyapp.viewer.di.ViewerUrl
import nl.entreco.giddyapp.viewer.ui.details.DetailModel
import javax.inject.Inject

class ViewerViewModel @Inject constructor(
    @ViewerUrl horseId: String?,
    fetchHorseUsecase: FetchHorseUsecase,
    private val swapHorseUsecase: SwapHorseUsecase
) : ViewModel(), SwapHorseUsecase.PreloadListener, DetailSheet.SlideListener {

    val details = ObservableField<DetailModel>()

    private val current = MutableLiveData<Horse>()
    private val next = MutableLiveData<Horse>()
    private val slider = MutableLiveData<Float>()

    init {
        swapHorseUsecase.onPreloadListener = this
        fetchHorseUsecase.go(FetchHorseRequest(horseId), onHorsesFetched())
    }

    private fun onHorsesFetched(): (FetchHorseResponse) -> Unit {
        return { response ->
            swapHorseUsecase.initWith(response.horses)
            swap()
        }
    }

    private fun swap() {
        swapHorseUsecase.go { popped, peeked ->
            current.postValue(popped)
            next.postValue(peeked)
            details.set(DetailModel(popped.details))
        }
    }

    fun current(): LiveData<Horse> {
        return current
    }

    fun next(): LiveData<Horse> {
        return next
    }

    fun toggler(): LiveData<Float> {
        return slider
    }

    fun onNext() {
        swap()
    }

    override fun onSlide(offset: Float) {
        slider.postValue(offset)
    }

    override fun onImageReady(horse: Horse) {
        if (current.value?.imageRef == horse.imageRef) current.postValue(horse)
        else if (next.value?.imageRef == horse.imageRef) next.postValue(horse)
    }
}