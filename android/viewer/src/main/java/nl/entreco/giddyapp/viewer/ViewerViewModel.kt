package nl.entreco.giddyapp.viewer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import nl.entreco.giddyapp.viewer.fetch.FetchHorseRequest
import nl.entreco.giddyapp.viewer.fetch.FetchHorseUsecase
import javax.inject.Inject
import kotlin.random.Random

class ViewerViewModel @Inject constructor(
    fetchHorseUsecase: FetchHorseUsecase
) : ViewModel() {

    private val current = MutableLiveData<Horse>()
    private val next = MutableLiveData<Horse>()

    init {
        fetchHorseUsecase.go(FetchHorseRequest()) { response ->
            current.postValue(response.current)
            next.postValue(response.next)
        }
    }

    fun current(): LiveData<Horse> {
        return current
    }

    fun next(): LiveData<Horse> {
        return next
    }

    fun onNext() {
        current.postValue(next.value)
        next.postValue(Horse("Random ${Random.nextInt(10000)}", random(), random(), "no image"))
    }

    private fun random() : String {
        // create a big random number - maximum is ffffff (hex) = 16777215 (dez)
        val nextInt = Random.nextInt(0xffffff + 1)
        // format it as hexadecimal string (with hashtag and leading zeros)
        return String.format("#%06x", nextInt)
    }
}