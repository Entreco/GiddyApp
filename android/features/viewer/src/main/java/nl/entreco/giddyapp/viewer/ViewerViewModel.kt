package nl.entreco.giddyapp.viewer

import android.net.Uri
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import nl.entreco.giddyapp.libcore.ui.DetailSheet
import nl.entreco.giddyapp.libhorses.Horse
import nl.entreco.giddyapp.libhorses.swap.SwapHorseUsecase
import nl.entreco.giddyapp.viewer.init.InitViewerRequest
import nl.entreco.giddyapp.viewer.init.InitViewerResponse
import nl.entreco.giddyapp.viewer.init.InitViewerUseCase
import nl.entreco.giddyapp.viewer.ratings.RateHorseRequest
import nl.entreco.giddyapp.viewer.ratings.RateHorseUsecase
import nl.entreco.giddyapp.viewer.ui.details.DetailModel
import javax.inject.Inject

class ViewerViewModel @Inject constructor(
    private val initViewerUseCase: InitViewerUseCase,
    private val swapHorseUsecase: SwapHorseUsecase,
    private val rateHorseUsecase: RateHorseUsecase
) : ViewModel(), SwapHorseUsecase.PreloadListener, DetailSheet.SlideListener {

    val details = ObservableField<DetailModel>()
    val icon = ObservableField<Uri?>()

    private val current = MutableLiveData<Horse>()
    private val next = MutableLiveData<Horse>()
    private val slider = MutableLiveData<Float>()

    init {
        swapHorseUsecase.onPreloadListener = this
    }

    fun load(horseId: String?) {
        current.postValue(Horse.Loading)
        details.set(DetailModel(Horse.Loading))
        swapHorseUsecase.clear()
        initViewerUseCase.go(InitViewerRequest(horseId), onInitialized())
    }

    private fun onInitialized(): (InitViewerResponse) -> Unit {
        return { response ->
            when (response) {
                is InitViewerResponse.Initialized -> {
                    icon.set(response.uri)
                    swapHorseUsecase.initWith(response.horses)
                    swap()
                }
                is InitViewerResponse.Error -> {
                    icon.set(null)
                    current.postValue(Horse.Error(response.msg))
                    details.set(DetailModel(Horse.Error(response.msg)))
                }
                is InitViewerResponse.NoUser -> {
                    icon.set(null)
                    current.postValue(Horse.Error("No user"))
                    details.set(DetailModel(Horse.Error("No user")))
                }
            }
        }
    }

    private fun swap() {
        swapHorseUsecase.go { popped, peeked ->
            current.postValue(popped)
            next.postValue(peeked)
            details.set(DetailModel(popped))
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

    fun onNext(like: Boolean) {
        swap()
    }

    override fun onSlide(offset: Float) {
        slider.postValue(offset)
    }

    override fun onImageReady(horse: Horse) {
        if (current.value?.imageRef == horse.imageRef) current.postValue(horse)
        else if (next.value?.imageRef == horse.imageRef) next.postValue(horse)
    }

    fun storeRating(rating: RateHorseRequest?) {
        rating?.let {
            rateHorseUsecase.store(it)
        }
    }

    fun submitRatings() {
        rateHorseUsecase.go { response ->
            Log.i("RATE", "RATING DONE: $response")
        }
    }

    override fun onCleared() {
        super.onCleared()
        swapHorseUsecase.clear()
        initViewerUseCase.clear()
    }
}