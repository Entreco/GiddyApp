package nl.entreco.giddyapp.creator

import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import nl.entreco.giddyapp.core.ChangeableField
import nl.entreco.giddyapp.core.LaunchHelper
import nl.entreco.giddyapp.core.images.ImagePicker
import nl.entreco.giddyapp.core.images.SelectedImage
import nl.entreco.giddyapp.libs.horses.HorseGender
import nl.entreco.giddyapp.libs.horses.create.CreateHorseRequest
import nl.entreco.giddyapp.libs.horses.create.CreateHorseUsecase
import javax.inject.Inject

class CreatorViewModel @Inject constructor(
    private val createHorseUsecase: CreateHorseUsecase
) : ViewModel() {

    val isValid = ObservableBoolean(false)
    val showImage = ObservableInt(View.VISIBLE)
    val name = ChangeableField("") { isValid.set(validInput()) }
    val description = ChangeableField("") { isValid.set(validInput()) }
    val gender = ChangeableField<HorseGender>(HorseGender.Unknown) { isValid.set(validInput()) }
    val image = ChangeableField<Uri> { isValid.set(validInput()) }
    private var startColor = ""
    private var endColor = ""

    fun selectedImages(images: List<SelectedImage>) {
        showImage.set(View.VISIBLE)
        images.firstOrNull()?.let {img ->
            Log.i("WAHOO", "path:$img")
            startColor = img.startColor
            endColor = img.endColor
            image.set(img.uri)
            showImage.set(View.GONE)
        }
    }

    fun tryUpload() {
        if (validInput()) {
            createHorseUsecase.go(
                CreateHorseRequest(
                    name.get()!!,
                    description.get()!!,
                    gender.get()!!,
                    image.get()!!,
                    startColor,
                    endColor
                )
            ) {
                // Bingo or not
            }
        }
    }

    private fun validInput(): Boolean {
        return name.get()?.isNotBlank() ?: false && description.get()?.isNotBlank() ?: false && gender.get() != null && image.get() != null
    }

    companion object {
        @JvmStatic
        @BindingAdapter("ga_preview")
        fun preview(view: ImageView, uri: Uri?){
            view.setImageURI(null)
            if(uri != null) {
                view.setImageURI(uri)
            }
        }
    }
}
