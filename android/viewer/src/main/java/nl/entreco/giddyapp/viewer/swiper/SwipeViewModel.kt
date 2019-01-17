package nl.entreco.giddyapp.viewer.swiper

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableFloat
import androidx.lifecycle.ViewModel
import nl.entreco.giddyapp.viewer.R
import nl.entreco.giddyapp.viewer.sound.PlaySoundUsecase
import javax.inject.Inject

class SwipeViewModel @Inject constructor(
    private val playSoundUsecase: PlaySoundUsecase
) : ViewModel() {

    val constraint = ObservableFloat(0F)
    val playing = ObservableBoolean(false)
    val price = ObservableField<String>()
    val nextTouched = ObservableBoolean(false)
    val nextProgress = ObservableFloat(0F)
    val next = ObservableField<SwipeHorseModel>()
    val current = ObservableField<SwipeHorseModel>()

    fun trackProgress(progress: Float, touched: Boolean) {
        nextTouched.set(touched)
        nextProgress.set(progress)

        if (!playing.get()) {
            when {
                progress > 0.6F && progress < 0.9F -> {
                    playing.set(true)
                    playSoundUsecase.play(R.raw.like)
                }
                progress > -0.9F && progress < -0.6F -> {
                    playing.set(true)
                    playSoundUsecase.play(R.raw.dislike)
                }
            }
        }
    }
}