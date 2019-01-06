package nl.entreco.giddyapp.viewer

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class ViewerViewModel(url: String?, query: String?) : ViewModel() {
    val url = ObservableField<String>((url ?: "Hello world") + (query ?: "query"))
}