package nl.entreco.giddyapp.viewer

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class ViewerViewModel(url: String?) : ViewModel() {
    val url = ObservableField<String>((url ?: "missing url") + " rand: ${Random.nextInt(10)}")
}