package nl.entreco.giddyapp.viewer.swiper

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import nl.entreco.giddyapp.viewer.Horse
import javax.inject.Inject

class SwipeViewModel @Inject constructor(): ViewModel() {
    val current = ObservableField<SwipeHorseModel>()
    val next = ObservableField<SwipeHorseModel>()
}