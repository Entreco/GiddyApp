package nl.entreco.giddyapp.viewer

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import nl.entreco.giddyapp.viewer.fetch.FetchHorseRequest
import nl.entreco.giddyapp.viewer.fetch.FetchHorseResponse
import nl.entreco.giddyapp.viewer.fetch.FetchHorseUsecase
import javax.inject.Inject

class ViewerViewModel @Inject constructor(
    horseId: String,
    fetchHorseUsecase: FetchHorseUsecase,
    private val horseProvider: HorseProvider
) : ViewModel(), HorseProvider.Listener {

    private val current = MutableLiveData<Horse>()
    private val next = MutableLiveData<Horse>()

    init {
        horseProvider.imageReadyListener = this
        fetchHorseUsecase.go(FetchHorseRequest(horseId), done())
    }

    private fun done(): (FetchHorseResponse) -> Unit {
        return { response ->
            horseProvider.addAll(response.horses)
            current.postValue(horseProvider.pop())
            next.postValue(horseProvider.peek())
        }
    }

    fun current(): LiveData<Horse> {
        return current
    }

    fun next(): LiveData<Horse> {
        return next
    }

    fun onNext() {
        current.postValue(horseProvider.pop())
        next.postValue(horseProvider.peek())
    }

    override fun onImageReady(horse: Horse) {
        if (current.value?.imageRef == horse.imageRef) current.postValue(horse)
        else if (next.value?.imageRef == horse.imageRef) next.postValue(horse)
    }
}