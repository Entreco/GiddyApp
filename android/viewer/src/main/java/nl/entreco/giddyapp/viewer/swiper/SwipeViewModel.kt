package nl.entreco.giddyapp.viewer.swiper

import android.view.View
import androidx.databinding.ObservableField
import androidx.databinding.ObservableFloat
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class SwipeViewModel @Inject constructor() : ViewModel() {

    val nextProgress = ObservableFloat(0F)
    val next = ObservableField<SwipeHorseModel>()
    val current = ObservableField<SwipeHorseModel>()

    fun trackProgress(view: View, progress: Float) {
        nextProgress.set(progress)
    }
}