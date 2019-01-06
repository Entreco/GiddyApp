package nl.entreco.giddyapp.viewer

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import nl.entreco.giddyapp.core.Api
import javax.inject.Inject

class ViewerViewModel @Inject constructor(api: Api, uri: String) : ViewModel() {
    val url = ObservableField<String>(uri + " rand: ${api.fetch()}")
}