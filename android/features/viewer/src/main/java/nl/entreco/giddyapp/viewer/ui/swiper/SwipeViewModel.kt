package nl.entreco.giddyapp.viewer.ui.swiper

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableFloat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import nl.entreco.giddyapp.libauth.UserService
import nl.entreco.giddyapp.libsound.PlaySoundUsecase
import nl.entreco.giddyapp.viewer.ratings.RateHorseRequest
import nl.entreco.giddyapp.viewer.ratings.RateHorseUsecase
import javax.inject.Inject

class SwipeViewModel @Inject constructor(
    private val playSoundUsecase: PlaySoundUsecase
) : ViewModel() {

    val constraint = ObservableFloat(0F)
    val playing = ObservableBoolean(false)
    val nextTouched = ObservableBoolean(false)
    val nextProgress = ObservableFloat(0F)
    val next = ObservableField<SwipeHorseModel>()
    val current = ObservableField<SwipeHorseModel>()

    private val ratings = MutableLiveData<RateHorseRequest>()
    fun ratings() : LiveData<RateHorseRequest> = ratings

    fun trackProgress(progress: Float, touched: Boolean) {
        nextTouched.set(touched)
        nextProgress.set(progress)

        if (!playing.get()) {
            val horseId = current.get()!!.horseId
            when {
                progress > 0.6F && progress < 0.9F -> {
                    playing.set(true)
                    playSoundUsecase.like()
                    ratings.postValue(RateHorseRequest(horseId, true))
                }
                progress > -0.9F && progress < -0.6F -> {
                    playing.set(true)
                    playSoundUsecase.dislike()
                    ratings.postValue(RateHorseRequest(horseId, false))
                }
            }
        }
    }

    fun onDisliked(view: SwipeHorseView) {
        view.skipToDislike()
    }

    fun onLiked(view: SwipeHorseView) {
        view.skipToLike()
    }
}