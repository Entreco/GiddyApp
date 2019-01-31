package nl.entreco.giddyapp.creator.ui.crop

import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import nl.entreco.giddyapp.core.ChangeableField
import nl.entreco.giddyapp.core.images.ImagePicker
import nl.entreco.giddyapp.core.images.SelectedImage
import nl.entreco.giddyapp.creator.CreatorState
import javax.inject.Inject

class CropViewModel @Inject constructor(
    private val picker: ImagePicker,
    private val crop: CreatorState.Crop?) : ViewModel() {

    val image = ChangeableField(crop?.image?.uri)

    private val event = MutableLiveData<CreatorState.Event>()
    fun events(): LiveData<CreatorState.Event> {
        return event
    }

    fun resize(bmp: Bitmap?, done:(SelectedImage) -> Unit) {
        crop?.image?.let{
            picker.resize(it, bmp, done)
        }
    }
}