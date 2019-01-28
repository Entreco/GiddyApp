package nl.entreco.giddyapp.core

import androidx.databinding.Observable
import androidx.databinding.ObservableField

class ChangeableField<T : Any>(initial: T? = null, onChange: () -> Unit = {}) : ObservableField<T>(initial) {

    private val callback = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            onChange()
        }
    }

    init {
        addOnPropertyChangedCallback(callback)
    }
}